package com.asterionix.exception;

import java.sql.SQLException;

import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

public class ViolatedConstraintNameExtracterImpl implements ViolatedConstraintNameExtracter{

	private SQLException e;
	public ViolatedConstraintNameExtracterImpl(SQLException sqle){
		
		this.e= sqle;
	}
	@Override
	public String extractConstraintName(SQLException sqle) {
		
		return e.getMessage();
	}

}
