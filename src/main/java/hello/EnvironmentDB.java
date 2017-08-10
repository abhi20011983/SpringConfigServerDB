package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Configuration
public class EnvironmentDB implements EnvironmentRepository {

  private static final String LABEL = "label";
  private static final String PROFILE = "profile";
  private static final String DEFAULT = "default";
  private static final String DEFAULT_PROFILE = null;
  private static final String DEFAULT_LABEL = null;

  @Autowired
  Querifier query;

  @Override
  public Environment findOne(String name, String profile, String label) {
    String[] profilesArr = StringUtils.commaDelimitedListToStringArray(profile);
    List<String> profiles = new ArrayList<String>(Arrays.asList(profilesArr.clone()));
    for (int i = 0; i < profiles.size(); i++) {
      if (DEFAULT.equals(profiles.get(i))) {
        profiles.set(i, DEFAULT_PROFILE);
      }
    }
    profiles.add(DEFAULT_PROFILE); // Default configuration will have 'null' profile
    profiles = sortedUnique(profiles);

    List<String> labels = Arrays.asList(label, DEFAULT_LABEL); // Default configuration will have
                                                               // 'null' label
    labels = sortedUnique(labels);

    System.out.println("here");
    /*
     * Query query = new Query(); query.addCriteria(Criteria.where(PROFILE).in(profiles.toArray()));
     * query.addCriteria(Criteria.where(LABEL).in(labels.toArray()));
     */
    Environment environment;
    try {
      // List<MongoPropertySource> sources = mongoTemplate.find(query, MongoPropertySource.class,
      // name);
      // sortSourcesByLabel(sources, labels);
      // sortSourcesByProfile(sources, profiles);
      environment = new Environment(name, profilesArr, label, null, label);

      /*
       * for (MongoPropertySource propertySource : sources) { String sourceName =
       * generateSourceName(name, propertySource); Map<String, Object> flatSource =
       * mapFlattener.flatten(propertySource.getSource()); PropertySource propSource = new
       * PropertySource(sourceName, flatSource); environment.add(propSource); }
       */
      String sourceName = "Databse - ICS config";
      Map<String, String> flatSource = new LinkedHashMap<String, String>();

      if (name.chars().allMatch(Character::isLetter)
          && profile.chars().allMatch(Character::isLetter)) {
        System.out.println(name);
      } else {
        System.out.println(name);
        System.out.println(profile);
        System.out.println("not allowed");
        return environment;
      }
      // name and label will always have to be only Letters, so no number and no special chars
      // allowed!

      if (profile.equals("default")) {
        profile = "";
      } else {
        profile = "-" + profile;
      }
      List<Map<String, Object>> rows =
          query.query("select * from [Configs].[dbo].[" + name + profile + "] order by id");

      if (rows.size() == 0) {
        return environment;
      }

      for (Map obj : rows) {

        if (obj.get("key") != null && obj.get("value") != null) {
          flatSource.put((String) obj.get("key").toString(), (String) obj.get("value").toString());
        }
      }

      PropertySource propSource = new PropertySource(sourceName, flatSource);
      environment.add(propSource);

    } catch (Exception e) {
      throw new IllegalStateException("Cannot load environment", e);
    }

    return environment;
  }

  private ArrayList<String> sortedUnique(List<String> values) {
    return new ArrayList<String>(new LinkedHashSet<String>(values));
  }



}
