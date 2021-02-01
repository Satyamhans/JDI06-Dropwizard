package com.flipkart.bean;


import java.util.Objects;

/**
 * @author pooja
 */
public class Course {
    private String courseId;
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

    public Course(String courseId, String courseName, String courseProfessor, double courseFee, String catalogueId, String courseDescription, int professorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseProfessor = courseProfessor;
        this.courseFee = courseFee;
        this.catalogueId = catalogueId;
        this.courseDescription = courseDescription;
        this.professorId = professorId;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProf(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }
    
    public String getCatalogueId() {
		return catalogueId;
	}

	public void setCatalogueId(String catalogueId) {
		this.catalogueId = catalogueId;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	
	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}
	
    public String toString() {
    	return courseId+"\t"+courseName+"\t"+professorId+"\t"+courseProfessor+"\t"+courseFee+"\t"+catalogueId+"\t"+courseDescription;
    }
    
    
}
