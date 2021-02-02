package com.flipkart.bean;


import java.util.Objects;

/**
 * @author pooja
 */

public class Course {

	private String courseName;
    private String courseProfessor;
    private double courseFee;
    private String catalogueId;
    private String courseDescription;
	private int professorId;
    
    public Course()
    {
    	
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    /**
     * @param courseId
     * @param courseName
     * @param courseProfessor
     * @param courseFee
     * @param catalogueId
     * @param courseDescription
     * @param professorId
     */
    public Course(String courseId, String courseName, String courseProfessor, double courseFee, String catalogueId, String courseDescription, int professorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseProfessor = courseProfessor;
        this.courseFee = courseFee;
        this.catalogueId = catalogueId;
        this.courseDescription = courseDescription;
        this.professorId = professorId;
    }

    private String courseId;
    /**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the courseProfessor
	 */
	public String getCourseProf() {
		return courseProfessor;
	}

	/**
	 * @param courseProfessor the courseProfessor to set
	 */
	public void setCourseProf(String courseProfessor) {
		this.courseProfessor = courseProfessor;
	}

	/**
	 * @return the courseFee
	 */
	public double getCourseFee() {
		return courseFee;
	}

	/**
	 * @param courseFee the courseFee to set
	 */
	public void setCourseFee(double courseFee) {
		this.courseFee = courseFee;
	}

	/**
	 * @return the catalogueId
	 */
	public String getCatalogueId() {
		return catalogueId;
	}

	/**
	 * @param catalogueId the catalogueId to set
	 */
	public void setCatalogueId(String catalogueId) {
		this.catalogueId = catalogueId;
	}

	/**
	 * @return the courseDescription
	 */
	public String getCourseDescription() {
		return courseDescription;
	}

	/**
	 * @param courseDescription the courseDescription to set
	 */
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	/**
	 * @return the professorId
	 */
	public int getProfessorId() {
		return professorId;
	}

	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

    public String toString() {
    	return courseId+", "+courseName;
    }
    
    
}
