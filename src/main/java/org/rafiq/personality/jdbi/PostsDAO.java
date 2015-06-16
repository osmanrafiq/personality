package org.rafiq.personality.jdbi;

import java.util.List;

import org.rafiq.personality.domain.Post;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * The {@link PostsDAO} defines the means necessary to retrieve and update posts in persistent storage 
 */
 @RegisterMapper(PostMapper.class)
public interface PostsDAO {
	@SqlUpdate("create table if not exists posts (party VARCHAR(32) NOT NULL, msg_id VARCHAR(32) NOT NULL, language VARCHAR(2) NOT NULL, created TIMESTAMP NOT NULL, message VARCHAR(10000) NOT NULL, CONSTRAINT pk_Posts PRIMARY KEY (party, msg_id, language))")
	void createPostsTable();

	@SqlQuery("select distinct msg_id, created, message from posts p where p.party = :party and created = (select distinct max(created) from posts where party = :party group by party) and language = 'da'")
	Post getLatestPost(@Bind("party") String party);
	
	@SqlQuery("select msg_id, created, message from posts where party = :party and language = :language")
	
	List<Post> getPosts(@Bind("party") String party, @Bind("language") String language);
	
	@SqlQuery("select msg_id, created, message from posts where party = :party and language = 'da' and msg_id not in (select msg_id from posts where party = :party and language = :language)")
	List<Post> getUntranslatedPosts(@Bind("party") String party, @Bind("language") String language);
	
	@SqlUpdate("insert into posts (party, msg_id, language, created, message) values (:party, :p.id, :language, :p.created, :p.message)")
	void insert(@Bind("party") String party, @BindBean("p") Post post, @Bind("language") String language);
}
