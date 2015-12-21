package com.quix.aia.cn.imo.utilities;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import com.quix.aia.cn.imo.constants.FolderNames;
import com.quix.aia.cn.imo.constants.FunctionNames;
import com.quix.aia.cn.imo.constants.HeaderNames;
import com.quix.aia.cn.imo.constants.ImageNames;
import com.quix.aia.cn.imo.constants.PageNames;


public class PathDetail {
	static Logger log = Logger.getLogger(PathDetail.class.getName());
	 public PathDetail()
	    {
	        pathDetail = null;
	        formDetail = null;
	        iterator = null;
	        dir = "/";
	        nameObjMapping = null;
	        navigationObj = null;
	        pathDetail = new LinkedList<KeyObjPair>();
	        formDetail = new LinkedList<KeyObjPair>();
	        
	        pathDetail.add(new KeyObjPair(PageNames.HOME, new PageObj(FunctionNames.HOME, dir + FolderNames.HOME_FOLDER + dir +"home", jsp, PageNames.HOME, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));

	        pathDetail.add(new KeyObjPair(PageNames.REFRESH, new PageObj(FunctionNames.HOME, dir + FolderNames.HOME_FOLDER + dir +"home", jsp, PageNames.REFRESH, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));

	        pathDetail.add(new KeyObjPair("DEMO", new PageObj("DEMO", dir + FolderNames.HOME_FOLDER + dir +"demo", jsp, "DEMO", ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));

	        
	        pathDetail.add(new KeyObjPair(PageNames.BU, new PageObj(FunctionNames.BU, dir + FolderNames.UTILITY_FOLDER + dir +"BU", jsp, PageNames.BU, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.BU_ADD, new FormObj(FunctionNames.BU_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"BUAdd", jsp, PageNames.BU_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.BRANCH, new PageObj(FunctionNames.BRANCH, dir + FolderNames.UTILITY_FOLDER + dir +"BRANCH", jsp, PageNames.BRANCH, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.BRANCH_ADD, new FormObj(FunctionNames.BRANCH_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"BRANCHAdd", jsp, PageNames.BRANCH_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.OFFICE, new PageObj(FunctionNames.OFFICE, dir + FolderNames.UTILITY_FOLDER + dir +"OFFICE", jsp, PageNames.OFFICE, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.OFFICE_ADD, new FormObj(FunctionNames.OFFICE_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"OFFICEAdd", jsp, PageNames.OFFICE_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.CITY, new PageObj(FunctionNames.CITY, dir + FolderNames.UTILITY_FOLDER + dir +"CITY", jsp, PageNames.CITY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.CITY_ADD, new FormObj(FunctionNames.CITY_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"CITYAdd", jsp, PageNames.CITY_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.DISTRICT, new PageObj(FunctionNames.DISTRICT, dir + FolderNames.UTILITY_FOLDER + dir +"DISTRICT", jsp, PageNames.DISTRICT, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.DISTRICT_ADD, new FormObj(FunctionNames.DISTRICT_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"DISTRICTAdd", jsp, PageNames.DISTRICT_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.SSC, new PageObj(FunctionNames.SSC, dir + FolderNames.UTILITY_FOLDER + dir +"SSC", jsp, PageNames.SSC, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.SSC_ADD, new FormObj(FunctionNames.SSC_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"SSCAdd", jsp, PageNames.SSC_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.Department, new PageObj(FunctionNames.SSC, dir + FolderNames.UTILITY_FOLDER + dir +"Department", jsp, PageNames.Department, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.Department_ADD, new FormObj(FunctionNames.Department_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"DepartmentAdd", jsp, PageNames.Department_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.Channel, new PageObj(FunctionNames.Channel, dir + FolderNames.UTILITY_FOLDER + dir +"Channel", jsp, PageNames.Channel, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.ChannelADD, new FormObj(FunctionNames.Channel_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"ChannelAdd", jsp, PageNames.ChannelADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.PRESENTER_CATEGORY, new PageObj(FunctionNames.PRESENTER_CATEGORY, dir + FolderNames.UTILITY_FOLDER + dir +"PresenterCategory", jsp, PageNames.PRESENTER_CATEGORY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.PRESENTER_CATEGORY_ADD, new FormObj(FunctionNames.PRESENTER_CATEGORY_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"PresenterCategory", jsp, PageNames.PRESENTER_CATEGORY_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	     
	        pathDetail.add(new KeyObjPair(PageNames.FESTIVE_CATEGORY, new PageObj(FunctionNames.FESTIVE_CATEGORY, dir + FolderNames.UTILITY_FOLDER + dir +"FestiveCategory", jsp, PageNames.FESTIVE_CATEGORY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.FESTIVE_CATEGORY_ADD, new FormObj(FunctionNames.FESTIVE_CATEGORY_ADD, dir + FolderNames.UTILITY_FOLDER + dir +"FestiveCategory", jsp, PageNames.FESTIVE_CATEGORY_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        formDetail.add(new KeyObjPair(PageNames.GoalConfigAdd, new FormObj(FunctionNames.GoalConfigAdd, dir + FolderNames.GOALSETTING_FOLDER + dir +"GoalConfig", jsp, PageNames.GoalConfigAdd, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        //E-GREETING
	        pathDetail.add(new KeyObjPair(PageNames.E_GREETING, new PageObj(FunctionNames.E_GREETING, dir + FolderNames.E_GREETING_FOLDER + dir +"E-Greeting", jsp, PageNames.E_GREETING, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.E_GREETING_ADD, new FormObj(FunctionNames.E_GREETING_ADD, dir + FolderNames.E_GREETING_FOLDER + dir +"E-Greeting", jsp, PageNames.E_GREETING_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        //
	        
	        
	        //GOAL SETTING PAGES 
	        pathDetail.add(new KeyObjPair(PageNames.IndividualGoal_YEARLY, new PageObj(FunctionNames.IndividualGoal_YEARLY, dir + FolderNames.GOALSETTING_FOLDER+ dir +"IndividualGoal_yearly", jsp, PageNames.IndividualGoal_YEARLY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.GoalBreakDown_YEARLY, new PageObj(FunctionNames.GoalBreakDown_YEARLY, dir + FolderNames.GOALSETTING_FOLDER+ dir +"GoalBreakDown_yearly", jsp, PageNames.GoalBreakDown_YEARLY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.GoalBreakDown_MONTHLY, new PageObj(FunctionNames.GoalBreakDown_MONTHLY, dir + FolderNames.GOALSETTING_FOLDER+ dir +"GoalBreakDown_monthly", jsp, PageNames.GoalBreakDown_MONTHLY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.TeamGoal_YEARLY, new PageObj(FunctionNames.TeamGoal_YEARLY, dir + FolderNames.GOALSETTING_FOLDER+ dir +"TeamGoal_yearly", jsp, PageNames.TeamGoal_YEARLY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.TeamGoal_MONTHLY, new PageObj(FunctionNames.TeamGoal_MONTHLY, dir + FolderNames.GOALSETTING_FOLDER+ dir +"TeamGoal_monthly", jsp, PageNames.TeamGoal_MONTHLY, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.History, new PageObj(FunctionNames.History, dir + FolderNames.GOALSETTING_FOLDER+ dir +"History", jsp, PageNames.History, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.ANNOUNCEMENT_MAIN, new PageObj(FunctionNames.ANNOUNCEMENT_MAIN, dir + FolderNames.ANNOUNCEMENT_FOLDER + dir +"AnnouncementMaintenance", jsp, PageNames.ANNOUNCEMENT_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.ANNOUNCEMENT_ADD, new FormObj(FunctionNames.ANNOUNCEMENT_ADD, dir + FolderNames.ANNOUNCEMENT_FOLDER + dir +"EopAnnouncementAdd", jsp, PageNames.ANNOUNCEMENT_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.INTERVIEW_MAIN, new PageObj(FunctionNames.INTERVIEW_MAIN, dir + FolderNames.INTERVIEW_FOLDER + dir +"InterviewMaintenance", jsp, PageNames.INTERVIEW_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.INTERVIEW_ADD, new FormObj(FunctionNames.INTERVIEW_ADD, dir + FolderNames.INTERVIEW_FOLDER + dir +"InterviewAdd", jsp, PageNames.INTERVIEW_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.INTERVIEW_ATTENDANCE, new PageObj(FunctionNames.INTERVIEW_ATTENDANCE, dir + FolderNames.INTERVIEW_FOLDER + dir +"InterviewAttendanceDetails", jsp, PageNames.INTERVIEW_ATTENDANCE, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.CANDIDATE_DOC_UPLOAD, new PageObj(FunctionNames.CANDIDATE_DOC_UPLOAD, dir + FolderNames.INTERVIEW_FOLDER + dir +"ViewCandidateDocUpload", jsp, PageNames.CANDIDATE_DOC_UPLOAD, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.CANDIDATE_INTERVIEW_DETAIL, new PageObj(FunctionNames.CANDIDATE_INTERVIEW_DETAIL, dir + FolderNames.INTERVIEW_FOLDER + dir +"ViewCandidateInterviewDetail", jsp, PageNames.CANDIDATE_INTERVIEW_DETAIL, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.INTERVIEW_UPLOAD_CSV, new FormObj(FunctionNames.INTERVIEW_UPLOAD_CSV, dir + FolderNames.INTERVIEW_FOLDER + dir +"InterviewUploadCSV", jsp, PageNames.INTERVIEW_UPLOAD_CSV,1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.HOLIDAY_MAIN, new PageObj(FunctionNames.HOLIDAY_MAIN, dir + FolderNames.HOLIDAY_FOLDER + dir +"HolidayMaintenance", jsp, PageNames.HOLIDAY_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.HOLIDAY_ADD, new FormObj(FunctionNames.HOLIDAY_ADD, dir + FolderNames.HOLIDAY_FOLDER + dir +"EopHolidayAdd", jsp, PageNames.HOLIDAY_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.HOLIDAY_UPLOAD_CSV, new FormObj(FunctionNames.HOLIDAY_UPLOAD_CSV, dir + FolderNames.HOLIDAY_FOLDER + dir +"HolidayUploadCSV", jsp, PageNames.HOLIDAY_UPLOAD_CSV,1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.EOP_MAIN, new PageObj(FunctionNames.EOP_MAIN, dir + FolderNames.EOP_FOLDER + dir +"EopMaintenance", jsp, PageNames.EOP_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.EOP_SCHEDULE_ADD, new FormObj(FunctionNames.EOP_SCHEDULE_ADD, dir + FolderNames.EOP_FOLDER + dir +"EopScheduleAdd", jsp, PageNames.EOP_SCHEDULE_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	       // pathDetail.add(new KeyObjPair(PageNames.EOP_SPECIALGROUP_ADD, new PageObj(FunctionNames.EOP_SPECIALGROUP_ADD, dir + FolderNames.EOP_FOLDER + dir +"EopAddSpecialGroup", jsp, PageNames.EOP_SPECIALGROUP_ADD, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.EOP_UPLOAD_CSV, new FormObj(FunctionNames.EOP_UPLOAD_CSV, dir + FolderNames.EOP_FOLDER + dir +"EopUploadCSV", jsp, PageNames.EOP_UPLOAD_CSV, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.EOP_ATTENDANCE_DETAILS, new PageObj(FunctionNames.EOP_ATTENDANCE_DETAILS, dir + FolderNames.EOP_FOLDER + dir +"EopAttendanceDetails", jsp, PageNames.EOP_ATTENDANCE_DETAILS, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.EOP_ATTENDANCE_ADD, new FormObj(FunctionNames.EOP_ATTENDANCE_ADD, dir + FolderNames.EOP_FOLDER + dir +"EopCandidateReg", jsp, PageNames.EOP_ATTENDANCE_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair(PageNames.USER_MAIN, new PageObj(FunctionNames.USER_MAIN, dir + FolderNames.USER_FOLDER + dir +"UserMaintenance", jsp, PageNames.USER_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.USER_ADD, new FormObj(FunctionNames.USER_ADD, dir + FolderNames.USER_FOLDER + dir +"UserAdd", jsp, PageNames.USER_ADD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.USER_RESET, new FormObj(FunctionNames.USER_RESET, dir + FolderNames.USER_FOLDER + dir +"UserResetPsw", jsp, PageNames.USER_RESET, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.USER_UPLOAD, new FormObj(FunctionNames.USER_UPLOAD, dir + FolderNames.USER_FOLDER + dir +"UserUploadCsv", jsp, PageNames.USER_UPLOAD, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.USER_PSWCHANGE, new FormObj(FunctionNames.USER_PSWCHANGE, dir + FolderNames.USER_FOLDER + dir +"UserPswChange", jsp, PageNames.USER_PSWCHANGE, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        
	        pathDetail.add(new KeyObjPair(PageNames.PRESENTER_MAINTENANCE, new PageObj(FunctionNames.PRESENTER_MAINTENANCE, dir + FolderNames.PRESENTER_FOLDER + dir +"PresenterMaintenance", jsp, PageNames.PRESENTER_MAINTENANCE, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        formDetail.add(new KeyObjPair(PageNames.PRESENTER_ADMINISTER, new FormObj(FunctionNames.PRESENTER_ADMINISTER, dir + FolderNames.PRESENTER_FOLDER + dir +"PresenterAdminister", jsp, PageNames.PRESENTER_ADMINISTER, 1, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        
	        pathDetail.add(new KeyObjPair("Message", new PageObj("Message", dir +"Message", jsp, "Message", ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.CC_TEST_MAIN, new PageObj(FunctionNames.CC_TEST_MAINTENANCE, dir + "CCTest" + dir +"CCTestMaintenance", jsp, PageNames.CC_TEST_MAIN, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	        pathDetail.add(new KeyObjPair(PageNames.VIEW_EAPPLICATION_FORM, new PageObj(FunctionNames.VIEW_EAPPLICATION_FORM, dir + FolderNames.INTERVIEW_FOLDER + dir +"ViewEapplicationForm", jsp, PageNames.VIEW_EAPPLICATION_FORM, ImageNames.IMAGE_NONE, HeaderNames.HEADER_NONE)));
	    }
	 
	  public PageObj getPageObj(Object key)
	    {
	    	//log.debug("[PathDetail] : getPageObj("+key+")...");
	        for(iterator = pathDetail.listIterator(); iterator.hasNext();)
	        {
	            nameObjMapping = (KeyObjPair)iterator.next();
	            if(nameObjMapping.getKey().equals(key))
	                return (PageObj)nameObjMapping.getObj();
	        }

	        return null;
	    }

	    
	    public FormObj getFormObj(Object key)
	    {
	    	//log.debug("[PathDetail] : getFormObj("+key+")...");
	        for(iterator = formDetail.listIterator(); iterator.hasNext();)
	        {
	            nameObjMapping = (KeyObjPair)iterator.next();
	            if(nameObjMapping.getKey().equals(key))
	                return (FormObj)nameObjMapping.getObj();
	        }

	        return null;
	    }
	    private LinkedList pathDetail;
	    private LinkedList formDetail;
	    private ListIterator iterator;
	    private String dir;
	    private static String jsp = ".jsp";
	    private static String png = ".png";
	    private KeyObjPair nameObjMapping;
	    private PageObj navigationObj;
}
