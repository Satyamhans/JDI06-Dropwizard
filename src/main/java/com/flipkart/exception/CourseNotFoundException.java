package com.flipkart.exception;

public class CourseNotFoundException extends  Exception {
	
	String courseId;

	public CourseNotFoundException(String courseId) {
		
		this.courseId = courseId;
		
	}

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		
		return courseId;
		
	}
}


