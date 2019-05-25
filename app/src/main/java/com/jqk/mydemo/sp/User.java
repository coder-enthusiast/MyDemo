package com.jqk.mydemo.sp;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;
    private Book book;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static class Book implements Serializable {
        private String bookName;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "bookName='" + bookName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", book=" + book +
                '}';
    }
}
