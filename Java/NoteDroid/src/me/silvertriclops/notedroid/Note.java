package me.silvertriclops.notedroid;

import java.util.Date;

public class Note {
	private long mId;
	private Date mCreationDate;
	private Date mModificationDate;
	private String mTitle;
	private String mBody;
	
	public Note(long id, Date creationDate, Date modificationDate, String title, String body) {
		mId = id;
		mCreationDate = creationDate;
		mModificationDate = modificationDate;
		mTitle = title;
		mBody = body;
	}
	
	public long getId() {
		return mId;
	}
	
	public Date getCreationDate() {
		return mCreationDate;
	}
	
	public Date getModificationDate() {
		return mModificationDate;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getBody() {
		return mBody;
	}
}
