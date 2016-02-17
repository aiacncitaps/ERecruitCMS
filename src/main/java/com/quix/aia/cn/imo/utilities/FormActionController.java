package com.quix.aia.cn.imo.utilities;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.data.announcement.Announcement;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.category.FestiveCategory;
import com.quix.aia.cn.imo.data.category.PresenterCategory;
import com.quix.aia.cn.imo.data.channel.Channel;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.city_dist.City_Dist;
import com.quix.aia.cn.imo.data.department.Department;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.egreeting.E_Greeting;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.goalsetting.GoalConfig;
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.office.Office;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.mapper.AnnouncementMaintenance;
import com.quix.aia.cn.imo.mapper.BUMaintenance;
import com.quix.aia.cn.imo.mapper.BranchMaintenance;
import com.quix.aia.cn.imo.mapper.ChannelMaintenance;
import com.quix.aia.cn.imo.mapper.CityDistrictMaintenance;
import com.quix.aia.cn.imo.mapper.CityMaintenance;
import com.quix.aia.cn.imo.mapper.DepartmentMaintenance;
import com.quix.aia.cn.imo.mapper.DistrictMaintenance;
import com.quix.aia.cn.imo.mapper.EgreetingMaintenance;
import com.quix.aia.cn.imo.mapper.EopAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.EopMaintenance;
import com.quix.aia.cn.imo.mapper.FestiveCategoryMaintenance;
import com.quix.aia.cn.imo.mapper.GoalConfigMaintenance;
import com.quix.aia.cn.imo.mapper.HolidayMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewMaintenance;
import com.quix.aia.cn.imo.mapper.OfficeMaintenance;
import com.quix.aia.cn.imo.mapper.PresenterCategoryMaintenance;
import com.quix.aia.cn.imo.mapper.PresenterMaintenance;
import com.quix.aia.cn.imo.mapper.SscMaintenance;
import com.quix.aia.cn.imo.mapper.UserMaintenance;

public class FormActionController {
	public FormActionController()
	{
	}
	 public static  Object formDataMapper(Object objToBeMapped, FormObj formObj, HttpServletRequest req){ 
	   
		 if(formObj.getKey().equals("UserAdd"))
	        {
			 UserMaintenance userMaintenance=new UserMaintenance();
	            if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY")){
	            	if(req.getParameter("userNo")!=null){
	            		return userMaintenance.getUser(Integer.parseInt(req.getParameter("userNo")) );
	            	}
	            }
	            if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
	            	userMaintenance.deleteUser(req.getParameter("catCode"), req);
	            if(formObj.isCanCreate())
	            {
	                if(formObj.getIndex() == 1){
	                    return userMaintenance.createNewUser((User)objToBeMapped, req);
	                    }
	            } else
	            if(formObj.isCanUpdate())
	            {
	                if(formObj.getIndex() == 1)
	                	return userMaintenance.updateUser((User)objToBeMapped, req);
	            } 
	            
	            	  else
	                return userMaintenance.mapForm1((User)objToBeMapped, req, formObj);
}
	       
		 else if(formObj.getKey().equals("EopAnnouncementAdd"))
          {
			   AnnouncementMaintenance announcementMaintenance = new AnnouncementMaintenance();
             if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
              {
                 if(req.getParameter("annoucementCode") != null)
                      return announcementMaintenance.getAnnouncement(Integer.parseInt(req.getParameter("annoucementCode")));
                  if(req.getSession().getAttribute("functionObj") != null)
                  {
               	   Announcement announcementObj = new Announcement();
               	   announcementObj = (Announcement)req.getSession().getAttribute("functionObj");
                   return announcementMaintenance.getAnnouncement(announcementObj.getAnnoucement_code());
                  }
              } else
                  if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
               	   announcementMaintenance.deleteAnnouncement(Integer.parseInt(req.getParameter("annoucementCode")),req);
              else
              if(formObj.isCanCreate())
              {
                  if(formObj.getIndex() == 1)
                      return announcementMaintenance.createNewAnnouncement((Announcement)objToBeMapped, req);
              } else
              if(formObj.isCanUpdate())
              {
                  if(formObj.getIndex() == 1)
                      return announcementMaintenance.updateAnnouncement((Announcement)objToBeMapped, req);
              } 
              else
              {
                  if(formObj.getIndex() == 1)
                      return announcementMaintenance.mapForm1((Announcement)objToBeMapped, req);
              }
          }
		
		 else if(formObj.getKey().equals("EopHolidayAdd"))
	        {
			 HolidayMaintenance holidayMaintenance = new HolidayMaintenance();
			 if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY")){
	              
				 if(req.getParameter("holiday_code") != null){
                     return holidayMaintenance.getHolidayBasedOnHolidayCode(Integer.parseInt(req.getParameter("holiday_code")));
				 }
	            }
			 else if(objToBeMapped == null && formObj.getFormType().equals("DELETE")){
				 holidayMaintenance.deleteHoliday(Integer.parseInt(req.getParameter("holiday_code")),req);
			 }
			 else if(formObj.isCanCreate())
	            {
	                if(formObj.getIndex() == 1)
	                	return holidayMaintenance.addHoliday((Holiday)objToBeMapped, req);
	            } 
			 else if(formObj.isCanUpdate())
	            {
	                if(formObj.getIndex() == 1)
	                	 return holidayMaintenance.updateHoliday((Holiday)objToBeMapped, req);
	            }
	           
	            else{
	            	 
	                return holidayMaintenance.mapForm1((Holiday)objToBeMapped, req);
	            }
	        }
	   
		 else if(formObj.getKey().equals("InterviewAdd"))
	        {
			 InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
			 if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY")){
	              
				 if(req.getParameter("interview_code") != null){
					 return interviewMaintenance.getInterviewBasedOnInterviewCode(Integer.parseInt(req.getParameter("interview_code")));
				 }
	            }
			 else if(objToBeMapped == null && formObj.getFormType().equals("DELETE")){
				 interviewMaintenance.deleteInterview(Integer.parseInt(req.getParameter("interview_code")),req);
			 }
			 else if(formObj.isCanCreate())
	            {
	                if(formObj.getIndex() == 1)
	                	return interviewMaintenance.addInterview((Interview)objToBeMapped, req);
	            } 
			 else if(formObj.isCanUpdate())
	            {
	                if(formObj.getIndex() == 1)
	                	 return interviewMaintenance.updateInterview((Interview)objToBeMapped, req);
	            }
	           
	            else{
	            	 
	                return interviewMaintenance.mapForm1((Interview)objToBeMapped, req);
	            }
	        }
	    
		// FOR CHANNEL
		 
		 if(formObj.getKey().equals("ChannelAdd"))
	        {
			 ChannelMaintenance channelMaintenance = new ChannelMaintenance();
			 
	           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
	            {
	               if(req.getParameter("CHANNEL_CODE") != null)
	                    return channelMaintenance.getChannel(Integer.parseInt(req.getParameter("CHANNEL_CODE")));
	                if(req.getSession().getAttribute("functionObj") != null)
	                {
	                	Channel ChannelObj = new Channel();
	                	ChannelObj = (Channel)req.getSession().getAttribute("functionObj");
	                  return channelMaintenance.getChannel(ChannelObj.getChannelCode());
	        	    
	                }
	            } else
	                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
	                	channelMaintenance.deleteChannel(Integer.parseInt(req.getParameter("CHANNEL_CODE")),req);
	            else
	            if(formObj.isCanCreate())
	            {
	                if(formObj.getIndex() == 1)
	                    return channelMaintenance.createNewChannel((Channel)objToBeMapped, req);
	            } else
	            if(formObj.isCanUpdate())
	            {
	                if(formObj.getIndex() == 1)
	                return channelMaintenance.updateChannel((Channel)objToBeMapped, req);
	            } else
	            {
	                if(formObj.getIndex() == 1)
	                    return channelMaintenance.mapForm1((Channel)objToBeMapped, req);
	            }
	        }
		 
		 //
		// FOR Department
		 
				 if(formObj.getKey().equals("DepartmentAdd"))
			        {
					 
					 DepartmentMaintenance departmentMaintenance = new DepartmentMaintenance();
					
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("DEPT_CODE") != null)
			                    return departmentMaintenance.getDepartment(Integer.parseInt(req.getParameter("DEPT_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	   Department DepartmentObj = new Department();
			             	  DepartmentObj = (Department)req.getSession().getAttribute("functionObj");
			                  return departmentMaintenance.getDepartment(DepartmentObj.getDeptCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	departmentMaintenance.deleteDepartment(Integer.parseInt(req.getParameter("DEPT_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                    return departmentMaintenance.createNewDepartment((Department)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return departmentMaintenance.updateDepartment((Department)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return departmentMaintenance.mapForm1((Department)objToBeMapped, req);
			            }
			        }
				 
				 //
				 
	// FOR Presenter Category
				 
				 if(formObj.getKey().equals("PresenterCategoryAdd"))
			        {
					 PresenterCategoryMaintenance  PresenterCategoryMaintenanceObj = new PresenterCategoryMaintenance();
					 
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("PRESENTER_CATEGORY_CODE") != null)
			                    return PresenterCategoryMaintenanceObj.getPresenterCategory(Integer.parseInt(req.getParameter("PRESENTER_CATEGORY_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			                	PresenterCategory PresenterCategoryObj = new PresenterCategory();
			                	PresenterCategoryObj = (PresenterCategory)req.getSession().getAttribute("functionObj");
			                  return PresenterCategoryMaintenanceObj.getPresenterCategory(PresenterCategoryObj.getPresenterCategoryCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	PresenterCategoryMaintenanceObj.deletePresenterCategory(Integer.parseInt(req.getParameter("PRESENTER_CATEGORY_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                    return PresenterCategoryMaintenanceObj.createNewPresenterCategory((PresenterCategory)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return PresenterCategoryMaintenanceObj.updatePresenterCategory((PresenterCategory)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return PresenterCategoryMaintenanceObj.mapForm1((PresenterCategory)objToBeMapped, req);
			            }
			        }
				   // 
				 
             // FOR Festive Category
				 
				 if(formObj.getKey().equals("FestiveCategoryAdd"))
			        {
					 
					 FestiveCategoryMaintenance festiveCategoryMaintenanceObj = new FestiveCategoryMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("FESTIVE_CATEGORY_CODE") != null)
			                    return festiveCategoryMaintenanceObj.getFestiveCategory(Integer.parseInt(req.getParameter("FESTIVE_CATEGORY_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			                	FestiveCategory FestiveCategoryObj = new FestiveCategory();
			                	FestiveCategoryObj = (FestiveCategory)req.getSession().getAttribute("functionObj");
			                  return festiveCategoryMaintenanceObj.getFestiveCategory(FestiveCategoryObj.getfestiveCategoryCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	festiveCategoryMaintenanceObj.deleteFestiveCategory(Integer.parseInt(req.getParameter("FESTIVE_CATEGORY_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                    return festiveCategoryMaintenanceObj.createNewFestiveCategory((FestiveCategory)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return festiveCategoryMaintenanceObj.updateFestiveCategory((FestiveCategory)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return festiveCategoryMaintenanceObj.mapForm1((FestiveCategory)objToBeMapped, req);
			            }
			        }
				   // 
		 
				 
				 //
		 
		// FOR BU
		 
				 if(formObj.getKey().equals("BUAdd"))
			        {
					  
					 BUMaintenance buMaintenance = new BUMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("BU_CODE") != null)
			                    return buMaintenance.getBU(Integer.parseInt(req.getParameter("BU_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	   Bu BuObj = new Bu();
			             	   BuObj = (Bu)req.getSession().getAttribute("functionObj");
			                  return buMaintenance.getBU(BuObj.getBuCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	buMaintenance.deleteBU(Integer.parseInt(req.getParameter("BU_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                    return buMaintenance.createNewBU((Bu)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return buMaintenance.updateBU((Bu)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return buMaintenance.mapForm1((Bu)objToBeMapped, req);
			            }
			        }
				    
		 // ----   DISTRICT----START------
				 
				 if(formObj.getKey().equals("DISTRICTAdd"))
			        {
					 DistrictMaintenance districtMaintenanceObj = new DistrictMaintenance();
					 
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("DISTRICT_CODE") != null)
			                    return districtMaintenanceObj.getDistrict(Integer.parseInt(req.getParameter("DISTRICT_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	  
			             	  District DistrictObj = new District();
			             	  DistrictObj = (District)req.getSession().getAttribute("functionObj");
			                  return districtMaintenanceObj.getDistrict(DistrictObj.getDistrictCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	districtMaintenanceObj.deleteDistrict(Integer.parseInt(req.getParameter("DISTRICT_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return districtMaintenanceObj.createNewDistrict((District)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return districtMaintenanceObj.updateDistrict((District)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return districtMaintenanceObj.mapForm1((District)objToBeMapped, req);
			            }
			        }
				 
	    // ----   DISTRICT----END------
				 
				 
				 
				 
       // ----   CITY----START------
				 
				 if(formObj.getKey().equals("CITYAdd"))
			        {
					 
					 CityMaintenance cityMaintenanceObj = new CityMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("CITY_CODE") != null &&  req.getParameter("CO") != null)
			                    return cityMaintenanceObj.getCity(req.getParameter("CITY_CODE"),req.getParameter("CO"));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	   
			                  City CityObj = new City();
			                  CityObj = (City)req.getSession().getAttribute("functionObj");
			                  return cityMaintenanceObj.getCity(CityObj.getCityName(),CityObj.getCo());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	cityMaintenanceObj.deleteCity(req.getParameter("CITY_CODE"),req,req.getParameter("CO"));
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return cityMaintenanceObj.createNewCity((City)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return cityMaintenanceObj.updateCity((City)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                	return cityMaintenanceObj.mapForm1((City)objToBeMapped, req);
			            }
			        }
				 
	    // ----   CITY----END------
				 
				 
				 
				 
				 
				 if(formObj.getKey().equals("BRANCHAdd"))
			        {
					 
					BranchMaintenance branchMain=new BranchMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			        	   if(req.getParameter("BRANCH_CODE")!=null){
			        		   return branchMain.getBranchBaseOnCode(Integer.parseInt(req.getParameter("BRANCH_CODE").trim()));
			        		   
			        	   }
			            } 
			           else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	branchMain.deleteBranch(Integer.parseInt(req.getParameter("BRANCH_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return branchMain.createNewBranch((Branch)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return branchMain.updateBranch((Branch)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                	return branchMain.mapForm1((Branch)objToBeMapped, req);
			            }
			        }
				 
				 
				 if(formObj.getKey().equals("OFFICEAdd"))
			        {
					 
					OfficeMaintenance officeMain=new OfficeMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			        	   if(req.getParameter("OFFICE_CODE")!=null){
			        		   return officeMain.getOfficeBaseOnCode(Integer.parseInt(req.getParameter("OFFICE_CODE").trim()));
			        		   
			        	   }
			            } 
			           else{
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	officeMain.deleteOffice(Integer.parseInt(req.getParameter("OFFICE_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return officeMain.createNewOffice((Office)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return officeMain.updateOffice((Office)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                	return officeMain.mapForm1((Office)objToBeMapped, req);
			            }
			         
			           }
			        }
				 
				 
       // ----   SSC Start-------
				 
				 if(formObj.getKey().equals("SSCAdd"))
			        {
					 
					 SscMaintenance SscMaintenanceObj = new SscMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("SSC_CODE") != null)
			                    return SscMaintenanceObj.getSsc(Integer.parseInt(req.getParameter("SSC_CODE")));
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	
			             	  Ssc SscObj = new Ssc();
			             	 SscObj = (Ssc)req.getSession().getAttribute("functionObj");
			                  return SscMaintenanceObj.getSsc(SscObj.getSscCode());
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	SscMaintenanceObj.deleteSsc(Integer.parseInt(req.getParameter("SSC_CODE")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return SscMaintenanceObj.createNewSsc((Ssc)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return SscMaintenanceObj.updateSsc((Ssc)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return SscMaintenanceObj.mapForm1((Ssc)objToBeMapped, req);
			            }
			        }
				 
				//------------- Ssc End  -------	
				 
                // -------E_Greeting Start-------
				 
				 if(formObj.getKey().equals("E_GreetingAdd"))
			        {
					 
					 
					 EgreetingMaintenance egreetingMaintenance= new EgreetingMaintenance();
			           if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
			            {
			               if(req.getParameter("E_GREETING_ID") != null)
			                    return egreetingMaintenance.getE_GreetingDetail(Integer.parseInt(req.getParameter("E_GREETING_ID")),req);
			                if(req.getSession().getAttribute("functionObj") != null)
			                {
			             	
			                	E_Greeting E_GreetingObj = new E_Greeting();
			                	E_GreetingObj = (E_Greeting)req.getSession().getAttribute("functionObj");
			                  return egreetingMaintenance.getE_GreetingDetail(E_GreetingObj.getEGreetingCode(),req);
			        	    
			                }
			            } else
			                if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	egreetingMaintenance.deleteE_Greeting(Integer.parseInt(req.getParameter("E_GREETING_ID")),req);
			            else
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
			                     return egreetingMaintenance.createNewEgreeting((E_Greeting)objToBeMapped, req);
			            } else
			            if(formObj.isCanUpdate())
			            {
			                if(formObj.getIndex() == 1)
			                return egreetingMaintenance.updateE_Greeting((E_Greeting)objToBeMapped, req);
			            } else
			            {
			                if(formObj.getIndex() == 1)
			                    return egreetingMaintenance.mapForm1((E_Greeting)objToBeMapped, req,formObj);
			            }
			        }
				 
				//------------- E_Greeting End  -------	 
				 
				 
				 
				 //
				 else if(formObj.getKey().equals("EopScheduleAdd"))
		          {
					   EopMaintenance eopMaintenance = new EopMaintenance();
		             if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
		              {
		                 if(req.getParameter("eventCode") != null)
		                      return eopMaintenance.getEvent(Integer.parseInt(req.getParameter("eventCode")));
		                	
		                  if(req.getSession().getAttribute("functionObj") != null)
		                  {
		                	  Event eventObj = new Event();
		                	  eventObj = (Event)req.getSession().getAttribute("functionObj");
		                      return eopMaintenance.getEvent(eventObj.getEvent_code());
		               	   
		                  }
		              } else
		                  if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
		                	  eopMaintenance.deleteEvent(Integer.parseInt(req.getParameter("eventCode")),req);
		                  else if (objToBeMapped == null && formObj.getFormType().equals("RESYNC"))
		                      eopMaintenance.resyncEvent(Integer.parseInt(req.getParameter("eventCode")), req);                
		              else
		              if(formObj.isCanCreate())
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopMaintenance.createNewEvent((Event)objToBeMapped, req);
		                	  
		              } else
		              if(formObj.isCanUpdate())
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopMaintenance.updateEvent((Event)objToBeMapped, req);
		              } 
		              else
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopMaintenance.mapForm1((Event)objToBeMapped, req);
		              }
		          }
					//presenter
				 else if(formObj.getKey().equals("PresenterAdminister"))
		          {
					 PresenterMaintenance presenterMaintenance = new PresenterMaintenance();
		             if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
		              {
		                 if(req.getParameter("presenterCode") != null)
		                      return presenterMaintenance.getPresenterDetail(Integer.parseInt(req.getParameter("presenterCode")));
		                  if(req.getSession().getAttribute("functionObj") != null)
		                  {
		               	   Presenter presenterObj = new Presenter();
		               	presenterObj = (Presenter)req.getSession().getAttribute("functionObj");
		                   return presenterMaintenance.getPresenterDetail(presenterObj.getPresenterCode());
		                  }
		              } else
		                  if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
		                	  presenterMaintenance.deletePresenter(req.getParameter("presenter_code"), req);
		              else
		              if(formObj.isCanCreate())
		              {
		                  if(formObj.getIndex() == 1)
		                      return presenterMaintenance.insertPresenter((Presenter)objToBeMapped, req);
		              } else
		              if(formObj.isCanUpdate())
		              {
		                  if(formObj.getIndex() == 1){}
		                      return presenterMaintenance.updatePresenter((Presenter)objToBeMapped, req);
		              } 
		              else
		              {
		                  if(formObj.getIndex() == 1)
		                      return presenterMaintenance.mapForm1((Presenter)objToBeMapped, req);
		              }
		          }
				 
				 else if(formObj.getKey().equals("HolidayUploadCSV"))
				    {
					 if(formObj.isCanCreate())
				        {
				           
				        } 
					 else{
				        return new HolidayMaintenance().cSVUpload((Holiday)objToBeMapped, req);	
				        }
				 }
				 
				 
				 if(formObj.getKey().equals("userUploadCsv"))
				    {
					 UserMaintenance userMaintenance=new UserMaintenance();
					 if(formObj.isCanCreate())
				        {
			            
				        }
						
				       return  userMaintenance.mapFormCSVUpload((User)objToBeMapped, req);	
				 
				    }
				 
				 else if(formObj.getKey().equals("InterviewUploadCSV"))
				    {
					 if(formObj.isCanCreate())
				        {
				           
				        } 
					 else{
				        return new InterviewMaintenance().cSVUpload((Interview)objToBeMapped, req);		
				        }
				 }
				 else if(formObj.getKey().equals("EopUploadCSV"))
				    {
					 if(formObj.isCanCreate())
				        {
				           
				        } 
					 else{
				        return new EopMaintenance().eopCSVUpload((Event)objToBeMapped, req);	
				        }
				 }
				 else if(formObj.getKey().equals("EopCandidateReg"))
		          {
					 EopAttendanceMaintenance eopAttendanceMaintenance = new EopAttendanceMaintenance();
		             if(objToBeMapped == null && formObj.getFormType().equals("VIEW") || objToBeMapped == null && formObj.getFormType().equals("MODIFY"))
		              {
		               if(req.getParameter("candidateCode") != null)
		                      return eopAttendanceMaintenance.getCandidate(Integer.parseInt(req.getParameter("candidateCode")));
		                  if(req.getSession().getAttribute("functionObj") != null)
		                  {
		                   EventCandidate candidateObj = new EventCandidate();
		                   candidateObj = (EventCandidate)req.getSession().getAttribute("functionObj");
		                   return eopAttendanceMaintenance.getCandidate(candidateObj.getCandidateCode());
		                  }
		            	
		              } else
		                  if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
		                	  eopAttendanceMaintenance.deleteCandidate(Integer.parseInt(req.getParameter("candidateCode")),req);
		                	  
		              else
		              if(formObj.isCanCreate())
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopAttendanceMaintenance.createNewCandidate((EventCandidate)objToBeMapped, req);
		              } else
		              if(formObj.isCanUpdate())
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopAttendanceMaintenance.updateCandidate((EventCandidate)objToBeMapped, req);
		                	
		              } 
		              else
		              {
		                  if(formObj.getIndex() == 1)
		                      return eopAttendanceMaintenance.mapForm1((EventCandidate)objToBeMapped, req);
		              }
		          }
				 
				 if(formObj.getKey().equals("GoalConfig"))
			        {
					  
					 GoalConfigMaintenance goalConfigMaintenance = new GoalConfigMaintenance();
			          
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
     				            return goalConfigMaintenance.createNewConfig((GoalConfig)objToBeMapped, req);
			            }
			            else
			            {
			                if(formObj.getIndex() == 1)
			                    return goalConfigMaintenance.mapForm1((GoalConfig)objToBeMapped, req);
			            }
			        }
				 
				 if(formObj.getKey().equals("CITY_DIST_Add"))
			        {
					  
					 CityDistrictMaintenance cityDistMain=new CityDistrictMaintenance();
			          
			            if(formObj.isCanCreate())
			            {
			                if(formObj.getIndex() == 1)
  				            return cityDistMain.createNewCity((City_Dist)objToBeMapped, req);
			            }else
			                  if(objToBeMapped == null && formObj.getFormType().equals("DELETE"))
			                	  cityDistMain.deleteCity(Integer.parseInt(req.getParameter("CITY_CODE")),req);
			                	
			            else
			            {
			                if(formObj.getIndex() == 1)
			                    return cityDistMain.mapForm1((City_Dist)objToBeMapped, req);
			            }
			        }
				
	    
		return objToBeMapped;
	}
  
}
