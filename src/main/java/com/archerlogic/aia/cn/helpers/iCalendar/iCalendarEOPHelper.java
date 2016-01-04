package com.archerlogic.aia.cn.helpers.iCalendar;

import com.archerlogic.aia.cn.helpers.iCalendar.iCalendarEventClass.EventType;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.database.HibernateFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class iCalendarEOPHelper
{

    //* ------------------------------------------------------------------------
    public boolean updateEOP(int eventCode, String ssoSessionId)
    {
        boolean success = false;

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateFactory.openSession();
            transaction = session.beginTransaction();

            // get interview from database
            Event eop = (Event) session.get(Event.class, eventCode);

            // get all the candidates that join to this interview from database so we can get the servicing agents
            List<EventCandidate> candidates = session.createCriteria(EventCandidate.class)
                    .add(Restrictions.eq("eventCode", eventCode))
                    .add(Restrictions.eq("status", true))
                    .list();

            // the list of agent codes to be updated to iCalendar
            ArrayList<String> agentCodes = new ArrayList<String>();

            if (candidates != null)
            {
                for (EventCandidate candidate : candidates)
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
                eop.setCalendarServiceError(0);
                session.save(eop);

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
                    String referenceId = String.valueOf(eop.getEvent_code()) + "-" + agentCode;
                    event.setReferenceId(referenceId);

                    event.setCompanyCode(String.valueOf(eop.getBranchCode()));
                    event.setAgentCode(agentCode);
                    event.setEventType(EventType.EOP);
                    event.setSubject(eop.getEventName());
                    event.setDescription(eop.getEopDescription());
                    event.setAddress(eop.getLocation());

                    // the date and time is stored as separate column in database
                    Date startDate = (Date) eop.getEventDate().clone();
                    startDate.setHours(eop.getStartTime().getHours());
                    startDate.setMinutes(eop.getStartTime().getMinutes());
                    startDate.setSeconds(eop.getStartTime().getSeconds());
                    event.setStartDate(startDate);

                    Date endDate = (Date) eop.getEventDate().clone();
                    endDate.setHours(eop.getEndTime().getHours());
                    endDate.setMinutes(eop.getEndTime().getMinutes());
                    endDate.setSeconds(eop.getEndTime().getSeconds());
                    event.setEndDate(endDate);

                    events.add(event);
                }

                iCalendarHelper helper = new iCalendarHelper();
                iCalendarResponseClass response = helper.update(events, ssoSessionId);

                // if response is not success, mark the interview as sync fail so it can be resync
                if (response == null || response.isSuccess() == false)
                {
                    eop.setCalendarServiceError(1);
                    session.save(eop);
                }
                else
                {
                    eop.setCalendarServiceError(0);
                    session.save(eop);

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
    public boolean deleteEOP(int eventCode, String ssoSessionId)
    {
        boolean success = false;

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateFactory.openSession();
            transaction = session.beginTransaction();

            // get interview from database
            Event eop = (Event) session.get(Event.class, eventCode);

            // get all the candidates that join to this interview from database so we can get the servicing agents
            List<EventCandidate> candidates = session.createCriteria(EventCandidate.class)
                    .add(Restrictions.eq("eventCode", eventCode))
                    .list();

            // the list of agent codes to be updated to iCalendar
            ArrayList<String> agentCodes = new ArrayList<String>();

            if (candidates != null)
            {
                for (EventCandidate candidate : candidates)
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
                eop.setCalendarServiceError(0);
                session.save(eop);

                success = true;
            }
            else
            {
                iCalendarHelper helper = new iCalendarHelper();
                iCalendarResponseClass response = helper.delete(eop.getEvent_code(), eop.getBranchCode(), agentCodes, ssoSessionId);

                // if response is not success, mark the interview as sync fail so it can be resync
                if (response == null || response.isSuccess() == false)
                {
                    eop.setCalendarServiceError(1);
                    session.save(eop);
                }
                else
                {
                    eop.setCalendarServiceError(0);
                    session.save(eop);

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
