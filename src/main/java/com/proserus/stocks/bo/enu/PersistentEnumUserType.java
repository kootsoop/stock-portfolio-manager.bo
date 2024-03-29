package com.proserus.stocks.bo.enu;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.proserus.stocks.bo.common.PersistentEnum;

public abstract class PersistentEnumUserType<T extends PersistentEnum<?>> implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException,
			SQLException {
		String id = rs.getString(names[0]);
		if (rs.wasNull()) {
			return null;
		}
		for (PersistentEnum<?> value : returnedClass().getEnumConstants()) {
			if (id.equals(value.getId())) {
				return value;
			}
		}
		throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id: " + id);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException,
			SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {
			st.setString(index, ((PersistentEnum<?>) value).getId());
		}
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public abstract Class<T> returnedClass();

	@Override
	public int[] sqlTypes() {
		//TODO is this ok?
		return new int[] { Types.VARCHAR };
	}

}
