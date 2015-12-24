package com.archerlogic.aia.cn.helpers.iCalendar;

import com.archerlogic.aia.cn.helpers.iCalendar.iCalendarEventClass.EventType;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.interview.InterviewCandidate;
import com.quix.aia.cn.imo.database.HibernateFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class iCalendarInterviewHelper
{

    //* ------------------------------------------------------------------------
    public boolean updateInterview(int interviewCode, String ssoSessionId)
    {
        boolean success = false;

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateFactory.openSession();
            transaction = session.beginTransaction();

            // get interview from database
            Interview interview = (Interview) session.get(Interview.class, interviewCode);

            // get all the candidates that join to this interview from database so we can get the servicing agents
            List<InterviewCandidate> candidates = session.createCriteria(InterviewCandidate.class)
                    .add(Restrictions.eq("interviewCode", interviewCode))
                    .add(Restrictions.eq("status", true))
                    .list();

            // the list of agent codes to be updated to iCalendar
            ArrayList<String> agentCodes = new ArrayList<String>();

            if (candidates != null)
            {
                for (InterviewCandidate candidate : candidates)
                {
                    if (agentCodes.contains(candidate.getServicingAgent()) == false)
                    {
                        agentCodes.add(candidate.getServicingAgent());
                    }
                }
            }

            if (agentCodes.isEmpty())
            {
                // no candidates or no agents, nothing to update to iCalendar
                interview.setCalendarServiceError(0);
                session.save(interview);

                success = true;
            }
            else
            {
                // prepare to call the iCalendar helper to send update web service
                ArrayList<iCalendarEventClass> events = new ArrayList<iCalendarEventClass>();

                for (String agentCode : agentCodes)
                {
                    iCalendarEventClass event = new iCalendarEventClass();

                    // reference id is hardcoded to be concat of interview code and agent code
                    String referenceId = String.valueOf(interview.getInterview_code()) + "-" + agentCode;
                    event.setReferenceId(referenceId);

                    event.setCompanyCode(String.valueOf(interview.getBranchCode()));
                    event.setAgentCode(agentCode);
                    event.setEventType(EventType.INTERVIEW);
                    event.setSubject(interview.getInterviewSessionName());
                    event.setDescription(interview.getInterviewMaterial());
                    event.setAddress(interview.getLocation());

                    // the date and time is stored as separate column in database
                    Date startDate = (Date) interview.getInterviewDate().clone();
                    startDate.setHours(interview.getStartTime().getHours());
                    startDate.setMinutes(interview.getStartTime().getMinutes());
                    startDate.setSeconds(interview.getStartTime().getSeconds());
                    event.setStartDate(startDate);

                    Date endDate = (Date) interview.getInterviewDate().clone();
                    endDate.setHours(interview.getEndTime().getHours());
                    endDate.setMinutes(interview.getEndTime().getMinutes());
                    endDate.setSeconds(interview.getEndTime().getSeconds());
                    event.setEndDate(endDate);

                    events.add(event);
                }

                iCalendarHelper helper = new iCalendarHelper();
                iCalendarResponseClass response = helper.update(events, ssoSessionId);

                // if response is not success, mark the interview as sync fail so it can be resync
                if (response == null || response.isSuccess() == false)
                {
                    interview.setCalendarServiceError(1);
                    session.save(interview);
                }
                else
                {
                    interview.setCalendarServiceError(0);
                    session.save(interview);

                    success = true;
                }
            }

            transaction.commit();
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }

            ex.printStackTrace();
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return success;
    }

    //* ------------------------------------------------------------------------
    public boolean deleteInterview(int interviewCode, String ssoSessionId)
    {
        boolean success = false;

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateFactory.openSession();
            transaction = session.beginTransaction();

            // get interview from database
            Interview interview = (Interview) session.get(Interview.class, interviewCode);

            // get all the candidates that join to this interview from database so we can get the servicing agents
            List<InterviewCandidate> candidates = session.createCriteria(InterviewCandidate.class)
                    .add(Restrictions.eq("interviewCode", interviewCode))
                    .list();

            // the list of agent codes to be updated to iCalendar
            ArrayList<String> agentCodes = new ArrayList<String>();

            if (candidates != null)
            {
                for (InterviewCandidate candidate : candidates)
                {
                    if (agentCodes.contains(candidate.getServicingAgent()) == false)
                    {
                        agentCodes.add(candidate.getServicingAgent());
                    }
                }
            }

            if (agentCodes.isEmpty())
            {
                // no candidates or no agents, nothing to update to iCalendar
                interview.setCalendarServiceError(0);
                session.save(interview);

                success = true;
            }
            else
            {
                iCalendarHelper helper = new iCalendarHelper();
                iCalendarResponseClass response = helper.delete(interview.getInterview_code(), interview.getBranchCode(), agentCodes, ssoSessionId);

                // if response is not success, mark the interview as sync fail so it can be resync
                if (response == null || response.isSuccess() == false)
                {
                    interview.setCalendarServiceError(1);
                    session.save(interview);
                }
                else
                {
                    interview.setCalendarServiceError(0);
                    session.save(interview);

                    success = true;
                }
            }

            transaction.commit();
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }

            ex.printStackTrace();
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return success;
    }

}
