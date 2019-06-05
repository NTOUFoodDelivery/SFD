package menu.model.javabean;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Menu {

  /**
   * Food_Id : 1 Food_Name : 薯餅蛋餅 Rest_Id : 1 Cost : 35 Description : 蛋餅包薯餅 Image :
   * https://imgur.com/Ck1KYGw.png
   */

  @SerializedName("Food_Id")
  private Long foodID;
  @SerializedName("Food_Name")
  private String foodName;
  @SerializedName("Rest_Id")
  private Long restID;
  @SerializedName("Cost")
  private int cost;
  @SerializedName("Description")
  private String description;
  @SerializedName("Image")
  private String image;

  public Long getFoodID() {
    return foodID;
  }

  public void setFoodID(Long foodID) {
    this.foodID = foodID;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public Long getRestID() {
    return restID;
  }

  public void setRestID(Long restID) {
    this.restID = restID;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("foodID", foodID)
        .append("foodName", foodName)
        .append("restID", restID)
        .append("cost", cost)
        .append("description", description)
        .append("image", image)
        .toString();
  }
}
