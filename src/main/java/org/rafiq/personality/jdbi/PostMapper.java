package org.rafiq.personality.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.rafiq.personality.domain.Post;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * The {@link PostMapper} maps a query result to a {@link Post} instance
 */
public class PostMapper implements ResultSetMapper<Post>{

	@Override
	public Post map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Timestamp timeStamp = r.getTimestamp("created");
		Date date = new Date(timeStamp.getTime());
		return new Post(r.getString("msg_id"), date, r.getString("message"));
	}

}
