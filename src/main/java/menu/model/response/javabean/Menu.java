package menu.model.response.javabean;

public class Menu {
    /**
     * Rest_Id : ybgufejk
     * Food_Id : tgfgehwjk
     * Food_Name : kvjfndb
     * Cost : 124
     * Description : ergftegwhj
     * Image : vtyfudj
     * status : true
     */

    private int Rest_Id;
    private int Food_Id;
    private String Food_Name;
    private int Cost;
    private String Description;
    private String Image;
    private boolean status;

    public int getRest_Id() {
        return Rest_Id;
    }

    public void setRest_Id(int Rest_Id) {
        this.Rest_Id = Rest_Id;
    }

    public int getFood_Id() {
        return Food_Id;
    }

    public void setFood_Id(int Food_Id) {
        this.Food_Id = Food_Id;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String Food_Name) {
        this.Food_Name = Food_Name;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int Cost) {
        this.Cost = Cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
