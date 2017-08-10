package hello;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Querifier {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  JdbcTemplate mySqlJdbcTemplate;

  public List<Map<String, Object>> query(String query) throws ClassNotFoundException {

    // Class modelClass = Class.forName("com.issgovernance.gac.UserRepository");
    List<Map<String, Object>> rows = new LinkedList();
    try {

      rows = jdbcTemplate.queryForList(query);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }


    return rows;

  }



  public Map execSP(String spName, String dbName, Map inParamMap) throws SQLException {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("dbo")
        .withCatalogName(dbName).withProcedureName(spName);

    Map<String, Object> simpleJdbcCallResult;

    if (inParamMap != null) {
      SqlParameterSource in = new MapSqlParameterSource(inParamMap);
      simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
    } else {
      simpleJdbcCallResult = simpleJdbcCall.execute();
    }
    return simpleJdbcCallResult;

  }



}
