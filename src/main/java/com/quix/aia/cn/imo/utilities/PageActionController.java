package com.quix.aia.cn.imo.utilities;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.data.channel.Channel;
import com.quix.aia.cn.imo.data.goalsetting.Individual_Goal;
import com.quix.aia.cn.imo.mapper.AnnouncementMaintenance;
import com.quix.aia.cn.imo.mapper.BUMaintenance;
import com.quix.aia.cn.imo.mapper.BranchMaintenance;
import com.quix.aia.cn.imo.mapper.CCTestMaintenance;
import com.quix.aia.cn.imo.mapper.ChannelMaintenance;
import com.quix.aia.cn.imo.mapper.CityMaintenance;
import com.quix.aia.cn.imo.mapper.DepartmentMaintenance;
import com.quix.aia.cn.imo.mapper.DistrictMaintenance;
import com.quix.aia.cn.imo.mapper.EgreetingMaintenance;
import com.quix.aia.cn.imo.mapper.EopAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.EopMaintenance;
import com.quix.aia.cn.imo.mapper.FestiveCategoryMaintenance;
import com.quix.aia.cn.imo.mapper.GoalMaintenance;
import com.quix.aia.cn.imo.mapper.HolidayMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewMaintenance;
import com.quix.aia.cn.imo.mapper.OfficeMaintenance;
import com.quix.aia.cn.imo.mapper.PresenterCategoryMaintenance;
import com.quix.aia.cn.imo.mapper.PresenterMaintenance;
import com.quix.aia.cn.imo.mapper.SscMaintenance;
import com.quix.aia.cn.imo.mapper.UserMaintenance;


public class PageActionController {
	
	 public PageActionController()
	    {
	    }

	 public static Pager dataListing(PageObj pageObj, HttpServletRequest requestParamters)
	    {
		 GoalMaintenance goalMaint=new GoalMaintenance();
		 if(pageObj.getKey().equals("UserMaintenance")){
			 
			 ImoUtilityData imoutill=new ImoUtilityData();
			 ArrayList<Channel> alChannel=imoutill.getActiveChannels(requestParamters);
			 UserMaintenance userMaintain=new UserMaintenance();
			 return userMaintain.getAllUserListing(requestParamters);
          }
		 
		 if(pageObj.getKey().equals("refresh")){
				ImoUtilityData imoUtility=new ImoUtilityData();
				imoUtility.getRefresh();
			
				return null;
	          }
		 
		//------------- Holiday Search  -------  
		 /* if(pageObj.getKey().equals("HolidayMaintenance"))
	        {
			   HolidayMaintenance holidayMaintenance = new HolidayMaintenance();
			   return holidayMaintenance.holidayParticularSearchListing(requestParamters);
	            
	        }*/
		  
		  //------ End ----------
		/*  if(pageObj.getKey().equals("AnnouncementMaintenance")){
			  AnnouncementMaintenance announcementMaintenance = new AnnouncementMaintenance();
			 return announcementMaintenance.announcementSearchListing(requestParamters);
		  }
		 */
		 
		 
		//------------- Channel Search Listing start-------   
		 if(pageObj.getKey().equals("Channel"))
		 {
				ChannelMaintenance  ChennalObject = new ChannelMaintenance();
			    return ChennalObject.getAllChannelListing();
		 }
		//------------- Channel Search Listing End-------   
		 
		 
		//------------- Department Search Listing start-------   
		 if(pageObj.getKey().equals("Department"))
		 {
			    DepartmentMaintenance  departmentMaintenanceObject = new DepartmentMaintenance();
			    return departmentMaintenanceObject.getAllDepartmentListing();
		 }
		//------------- Department Search Listing End-------   
		 
		//------------- PresenterCategory Search Listing start-------   
		 if(pageObj.getKey().equals("PresenterCategory"))
		 {
				PresenterCategoryMaintenance PresenterCategoryobject=new PresenterCategoryMaintenance();
				return PresenterCategoryobject.getAllPresenterCategoryListing();
		 }
		//------------- PresenterCategory Search Listing End-------   
		 
		//------------- FestiveCategory Search Listing start-------   
		 if(pageObj.getKey().equals("FestiveCategory"))
		 {
			 FestiveCategoryMaintenance FestiveCategoryObject=new FestiveCategoryMaintenance();
				return FestiveCategoryObject.getAllFestiveCategoryListing();
		 }
		//------------- FestiveCategory Search Listing End-------   
		  
		  //------------- BU Search Listing start-------   
			 if(pageObj.getKey().equals("BU"))
			 {
					BUMaintenance BUobject=new BUMaintenance();
					return BUobject.getAllBUListing();
			 }
			 if(pageObj.getKey().equals("DEMO"))
			 {
					BUMaintenance BUobject=new BUMaintenance();
					return BUobject.getAllBUListing();
			 }
			//------------- BU Search Listing End-------   
			 
		  
			//------------- DISTRICT Search Listing start-------   
			 if(pageObj.getKey().equals("DISTRICT"))
			 {
				    DistrictMaintenance Districtobject=new DistrictMaintenance();
					return Districtobject.getAllDistrictListing();
			 }
			//-------------  DISTRICT Listing End-------   
			 
			 
			//------------- CITY Search Listing start-------   
			 if(pageObj.getKey().equals("CITY"))
			 {
					CityMaintenance Cityobject=new CityMaintenance();
					return Cityobject.getAllCityListing();
			 }
			 
			 
			//------------- CITY Search Listing End-------   
			 
			 
			 if(pageObj.getKey().equals("BRANCH")){
				 BranchMaintenance branchMain=new BranchMaintenance();
				 return branchMain.getAllBranchListing();
			  }
			 
			 if(pageObj.getKey().equals("OFFICE")){
				 OfficeMaintenance officeMain=new OfficeMaintenance();
				 return officeMain.getAllOfficeListing();
			  }
			 
			//------------- SSC Search Listing start-------   
			 if(pageObj.getKey().equals("SSC"))
			 {
					SscMaintenance Sscobject=new SscMaintenance();
					return Sscobject.getAllSscListing();
			 }
			//------------- SSC Search Listing End-------   
			 
			 /*
			  if(pageObj.getKey().equals("InterviewMaintenance"))
		        {
				   InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
				   return interviewMaintenance.interviewAllSearchListing();
		            
		        }	*/ 
			 if(pageObj.getKey().equals("PresenterMaintenance"))
				 {
						PresenterMaintenance obj=new PresenterMaintenance();
						return obj.retrieveActivityListing(obj.getAllPresenter(requestParamters));
				 }
			 
			 if(pageObj.getKey().equals("InterviewMaintenance"))
		        {
				   InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
				   return interviewMaintenance.getAllinterview(requestParamters);
		            
		        }
			 
			 //individual goal
			 if(pageObj.getKey().equals("IndividualGoal_Y")){
				 Individual_Goal obj= goalMaint.getIndividualGoal(requestParamters);
	          }
			 //year goal breakdown
			 if(pageObj.getKey().equals("GoalBreakDown_Y")  ||  pageObj.getKey().equals("GoalBreakDown_M")){
				 Individual_Goal obj= goalMaint.getIndividualGoal(requestParamters);
				  if(requestParamters.getParameter("reset")!=null){
						 if(requestParamters.getParameter("reset").equals("true"))
							 goalMaint.getResetGoalBreakDown(requestParamters,obj);
					 }else  
						  goalMaint.getGoalBreakDown(requestParamters);
	          }
			 //month goal breakdown
			/* if(pageObj.getKey().equals("GoalBreakDown_M")){
				 Individual_Goal obj= goalMaint.getIndividualGoal(requestParamters);
				 if(requestParamters.getParameter("reset")!=null){
					 if(requestParamters.getParameter("reset").equals("true"))
						 goalMaint.getResetGoalBreakDown(requestParamters,obj);
				 }else  
					  goalMaint.getMonthGoalBreakDown(requestParamters);
	          }
			*/
			 //year team goal
			 if(pageObj.getKey().equals("TeamGoal_y") || pageObj.getKey().equals("TeamGoal_M")){
				  goalMaint.getYearTeamGoal(requestParamters);
	          }
			 //history
			 if(pageObj.getKey().equals("history"))
			 {
					
					return goalMaint.retrieveHistoryListing(goalMaint.getAllHistory(requestParamters));
			 }
			 if(pageObj.getKey().equals("InterviewAttendanceDetails"))
		        {
				   InterviewAttendanceMaintenance interviewMaintenance = new InterviewAttendanceMaintenance();
		            return interviewMaintenance.interviewAttendanceListing(requestParamters);
		        }
			
			  if(pageObj.getKey().equals("EopAttendanceDetails"))
		        {
				  EopAttendanceMaintenance attendanceMaintenance = new EopAttendanceMaintenance();
				  return attendanceMaintenance.eopAttendanceListing(requestParamters);
		            
		        }
			  
			  if(pageObj.getKey().equals("AnnouncementMaintenance")){
				  AnnouncementMaintenance announcementMaintenance = new AnnouncementMaintenance();
				 return announcementMaintenance.announcementListing(requestParamters);
			  }
			  
			  if(pageObj.getKey().equals("EopMaintenance"))
				 {
				  		EopMaintenance eopMaintenance=new EopMaintenance();
						return eopMaintenance.eventListing(requestParamters);
				 } 
			  
			  if(pageObj.getKey().equals("E_Greeting")){
				  EgreetingMaintenance egreetingMaintenance = new EgreetingMaintenance();
					 return egreetingMaintenance.eGreetingListing(requestParamters);
			  }
			  
			  
			 if(requestParamters.getSession().getAttribute("HOL_SEARCH_OBJ")!=null)
				 requestParamters.getSession().removeAttribute("HOL_SEARCH_OBJ");
			 if(requestParamters.getSession().getAttribute("INT_SEARCH_OBJ")!=null)
				 requestParamters.getSession().removeAttribute("INT_SEARCH_OBJ");
			 if(requestParamters.getSession().getAttribute("ANN_SEARCH_OBJ")!=null)
				 requestParamters.getSession().removeAttribute("ANN_SEARCH_OBJ");
			 if(requestParamters.getSession().getAttribute("EOP_SEARCH_OBJ")!=null)
				 requestParamters.getSession().removeAttribute("EOP_SEARCH_OBJ");
			 if(requestParamters.getSession().getAttribute("EventObj")!=null)
				 requestParamters.getSession().removeAttribute("EventObj");
			 if(requestParamters.getSession().getAttribute("EOP_ATTENDANCE_LIST")!=null)
				 requestParamters.getSession().removeAttribute("EOP_ATTENDANCE_LIST");
		 return null;
	    }
	 
	  public static Pager searchListing(PageObj pageObj, HttpServletRequest requestParamters)
	    {
		 
		 
		  if(pageObj.getKey().equals("AnnouncementMaintenance")){
			  AnnouncementMaintenance announcementMaintenance = new AnnouncementMaintenance();
			 return announcementMaintenance.announcementSearchListing(requestParamters);
		  }
		  
		  if(pageObj.getKey().equals("UserMaintenance")){
				 UserMaintenance userMaintain=new UserMaintenance();
	             return userMaintain.getUserListing(requestParamters);
	       }
		//------------- Holiday Search  -------  
		  if(pageObj.getKey().equals("HolidayMaintenance"))
	        {
			   HolidayMaintenance holidayMaintenance = new HolidayMaintenance();
			   return holidayMaintenance.holidayParticularSearchListing(requestParamters);
	            
	        }
		  
		  //------ End ----------
		  
		  
		//------------- Channel  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("Channel")){
			  ChannelMaintenance Channelobject=new ChannelMaintenance();
			 return Channelobject.ChannelSearchListing(requestParamters);
		  }
		//------------- Channel  Particular Search Listing End-------  
		  
			//-------------PresenterCategory Particular Search Listing start-------   
		  if(pageObj.getKey().equals("PresenterCategory")){
			  PresenterCategoryMaintenance PresenterCategoryobject=new PresenterCategoryMaintenance();
			 return PresenterCategoryobject.PresenterCategorySearchListing(requestParamters);
		  }
		//------------- PresenterCategory  Particular Search Listing End------- 
		  
		  
		//-------------FestiveCategory Particular Search Listing start-------   
		  if(pageObj.getKey().equals("FestiveCategory")){
			  FestiveCategoryMaintenance FestiveCategoryobject=new FestiveCategoryMaintenance();
			 return FestiveCategoryobject.FestiveCategorySearchListing(requestParamters);
		  }
		//------------- FestiveCategory  Particular Search Listing End------- 
	  
		//------------- Department  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("Department")){
			  DepartmentMaintenance Departmentobject=new DepartmentMaintenance();
			 return Departmentobject.DepartmentSearchListing(requestParamters);
		  }
		//------------- Department  Particular Search Listing End------- 
		  
		  
		//------------- BU  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("BU")){
			  BUMaintenance BUobject=new BUMaintenance();
			 return BUobject.BUSearchListing(requestParamters);
		  }
		//------------- BU  Particular Search Listing End------- 
		  
		  
		//------------- District  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("DISTRICT")){
			  DistrictMaintenance Districtobject=new DistrictMaintenance();
			 return Districtobject.DistrictSearchListing(requestParamters);
		  }
		//------------- District  Particular Search Listing End-------  
		  
		  
		//------------- City  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("CITY")){
			  CityMaintenance Cityobject=new CityMaintenance();
			 return Cityobject.CitySearchListing(requestParamters);
		  }
		//------------- City   Particular Search Listing End-------  
		  
		  if(pageObj.getKey().equals("BRANCH")){
			 BranchMaintenance branchMain=new BranchMaintenance();
			 return branchMain.BranchSearchListing(requestParamters);
		  }
		  
		//------------- SSC  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("SSC")){
			  SscMaintenance Sscobject=new SscMaintenance();
			 return Sscobject.SscSearchListing(requestParamters);
		  }
		//------------- SSC  Particular Search Listing End------- 
		  
		  if(pageObj.getKey().equals("OFFICE")){
			  OfficeMaintenance officeMain=new OfficeMaintenance();
			 return officeMain.OfficeSearchListing(requestParamters);
		  }
		  
		//------------- E_Greeting  Particular Search Listing start-------   
		  if(pageObj.getKey().equals("E_Greeting")){
			  EgreetingMaintenance egreetingMaintenance = new EgreetingMaintenance();
				 return egreetingMaintenance.eGreetingSearchListing(requestParamters);
		  }
		//------------- E_Greeting  Particular Search Listing End------- 
		  
		  
		  //presenter form search
		  if(pageObj.getKey().equals("PresenterMaintenance"))
			 {
					PresenterMaintenance obj=new PresenterMaintenance();
					return obj.retrieveActivityListing(obj.searchPresenter(requestParamters));
			 } 
		  if(pageObj.getKey().equals("EopMaintenance"))
			 {
			  		EopMaintenance eopMaintenance=new EopMaintenance();
					return eopMaintenance.eventSearchListing(requestParamters);
			 } 
		  if(pageObj.getKey().equals("InterviewMaintenance"))
	        {
			   InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
			   return interviewMaintenance.interviewAllSearchListing(requestParamters);
	            
	        }
		  if(pageObj.getKey().equals("CCTestMaintenance"))
          {
            CCTestMaintenance ccTestMaintenance = new CCTestMaintenance();

           
            if(requestParamters.getSession().getAttribute("CC_TEST_AGENT_LIST")!=null)
                return ccTestMaintenance.updateCCTestStatus(requestParamters);
            else
                return ccTestMaintenance.cctestAllSearchListing(requestParamters);                 
          }
		  if(pageObj.getKey().equals("EopAttendanceDetails"))
	        {
			  EopAttendanceMaintenance attendanecMaintenance = new EopAttendanceMaintenance();
			   return attendanecMaintenance.updateCandidateCompleteStatus(requestParamters);
	            
	        }
		  if(pageObj.getKey().equals("InterviewAttendanceDetails"))
	        {
			   InterviewAttendanceMaintenance attendanecMaintenance = new InterviewAttendanceMaintenance();
			   return attendanecMaintenance.updateCandidateCompleteStatus(requestParamters);
	            
	        }
		  if(pageObj.getKey().equals("ViewEapplicationForm"))
	        {
			    PathDetail pathDetail = new PathDetail();
			     
		        pageObj = pathDetail.getPageObj("InterviewAttendanceDetails");
		        requestParamters.getSession().setAttribute("pageObj", pageObj);
		        requestParamters.setAttribute("contentPage", pageObj);
			    InterviewAttendanceMaintenance attendanecMaintenance = new InterviewAttendanceMaintenance();
			   return attendanecMaintenance.updateCandidateCompleteStatus(requestParamters);
	            
	        }
		  return null;

	    }
}
