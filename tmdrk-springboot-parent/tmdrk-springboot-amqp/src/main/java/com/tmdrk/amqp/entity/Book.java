package com.tmdrk.amqp.entity;

/**
 * @ClassName Book
 * @Description
 * @Author zhoujie
 * @Date 2020/5/10 17:49
 * @Version 1.0
 **/
public class Book {
    private Integer id;
    private String name;
    public Book(){

    }

    public Book(int id,String name){
        this.id=id;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
