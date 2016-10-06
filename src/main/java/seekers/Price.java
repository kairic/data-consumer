package seekers;

import org.springframework.data.annotation.Id;

public class Price {

  @Id
  private String id;
  private String value;
  private String timestamp;

  public Price() {}

  public Price(String value, String timestamp) {
    this.value = value;
    this.timestamp = timestamp;
  }

  public String getValue() {
    return this.value;
  }

  public String getTimestamp() {
    return this.timestamp;
  }

}
