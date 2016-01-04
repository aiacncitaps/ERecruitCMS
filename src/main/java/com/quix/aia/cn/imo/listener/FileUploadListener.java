/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date              Developer          Change Description
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
package com.quix.aia.cn.imo.listener;



import java.util.logging.Logger;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {

	static Logger log = Logger.getLogger(FileUploadListener.class.getName());
	private long num100Ks = 0;

	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int whichItem = 0;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;
	
	
	public void update(long bytesRead, long contentLength, int items) {

		if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		long nowNum100Ks = bytesRead / 100;
		// Only run this code once every 1K
		if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
				percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
			}
			//System.out.println(getMessage());
		}
	}

	public String getMessage() {
		if (theContentLength == -1) {
			//return "" + theBytesRead + " of Unknown-Total bytes have been read.";
			return "0";
		} else {
			return percentDone+"";
		}

	}

	public long getNum100Ks() {
		return num100Ks;
	}

	public void setNum100Ks(long num100Ks) {
		this.num100Ks = num100Ks;
	}

	public long getTheBytesRead() {
		return theBytesRead;
	}

	public void setTheBytesRead(long theBytesRead) {
		this.theBytesRead = theBytesRead;
	}

	public long getTheContentLength() {
		return theContentLength;
	}

	public void setTheContentLength(long theContentLength) {
		this.theContentLength = theContentLength;
	}

	public int getWhichItem() {
		return whichItem;
	}

	public void setWhichItem(int whichItem) {
		this.whichItem = whichItem;
	}

	public int getPercentDone() {
		return percentDone;
	}

	public void setPercentDone(int percentDone) {
		this.percentDone = percentDone;
	}

	public boolean isContentLengthKnown() {
		return contentLengthKnown;
	}

	public void setContentLengthKnown(boolean contentLengthKnown) {
		this.contentLengthKnown = contentLengthKnown;
	}
}