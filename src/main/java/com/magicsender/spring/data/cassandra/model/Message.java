package com.magicsender.spring.data.cassandra.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class Message {

  @PrimaryKey
  private UUID id;

  private String email;
  private String title;
  private String content;
  private Integer magicnumber;

  public Message() {
  }

  public Message(UUID id, String email, String title, String content, Integer magicnumber) {
    this.id = id;
    this.email = email;
    this.title = title;
    this.content = content;
    this.magicnumber = magicnumber;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getMagicnumber() {
    return magicnumber;
  }

  public void setMagicnumber(Integer magicnumber) {
    this.magicnumber = magicnumber;
  }

  @Override
  public String toString() {
    return "Message{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", magicnumber=" + magicnumber +
            '}';
  }
}
