package model;

public class List {
    private Integer id_list;
    private String date;
    private Integer id_user_Fk;

    public List(Integer id_list, String date, Integer id_user_Fk) {
        this.id_list = id_list;
        this.date = date;
        this.id_user_Fk = id_user_Fk;
    }

    public List(String date, Integer id_user_Fk) {
        this.date = date;
        this.id_user_Fk = id_user_Fk;
    }

    public Integer getId_list() {
        return id_list;
    }

    public void setId_list(Integer id_list) {
        this.id_list = id_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId_user_Fk() {
        return id_user_Fk;
    }

    public void setId_user_Fk(Integer id_user_Fk) {
        this.id_user_Fk = id_user_Fk;
    }
}
