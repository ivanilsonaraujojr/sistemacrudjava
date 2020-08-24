package com.ivanilsonjr.model.dao;

import com.ivanilsonjr.db.DB;
import com.ivanilsonjr.model.dao.impl.ClienteJDBC;

public class DaoFactory {
	public static ClienteDao createClienteDao() {
		return new ClienteJDBC(DB.getConnection());
	}
}
