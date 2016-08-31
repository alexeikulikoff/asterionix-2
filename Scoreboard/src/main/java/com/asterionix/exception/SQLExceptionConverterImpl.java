package com.asterionix.exception;

import java.sql.SQLException;

import org.hibernate.JDBCException;
import org.hibernate.exception.spi.SQLExceptionConverter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

public class SQLExceptionConverterImpl implements SQLExceptionConverter {

	private static final long serialVersionUID = 1L;

	private ViolatedConstraintNameExtracter extractor;
	
	public SQLExceptionConverterImpl(ViolatedConstraintNameExtracter extractor){
		
		this.extractor = extractor;
		
	}
	@Override
	public JDBCException convert(SQLException sqlException, String message, String sql) {
		   final int errorCode = sqlException.getErrorCode();
		   String violationName = extractor.extractConstraintName(sqlException);
		   System.out.println("errorCode :" + errorCode);
		   System.out.println("Violation :" + violationName);
		   JDBCException ex = new JDBCException(sql, sqlException);
		   
		   
		return ex;
	}

}
