package com.harsh.myfirstapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity // Ye btata he ki ye class MySQL me 1 Table he
public class SupportTicket {
    @Id   // har table me 1 unique ID hoti he (Primary Key) ye annotation us var. ko ID define karta he
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Iska matlab he ID hame nahi deni, Database automatic apne according Increment krta rhega
    private int id;

    @NotBlank(message = "Issue should not Empty!!")
    @Size(min = 10, message = "Please write issue in detail, with minimum 10 characters")
    private String issue;

    @NotBlank(message = "Status is Mandatory!!!")
    private String status;

    // Data kab create hua uska Date and Time add krne k liye automatically
    @CreationTimestamp     // issue jab bhi new data Post hoga table me to Automatic Date and time add ho jaega uske saath MySQL table me
    @Column(updatable = false) // isse CreatedAt kabhi change nahi hoga mtlb jab wo data add hua uska Time wahi rhega createdAt me (ye nhi krte to update k time ye null ho jata)
    private LocalDateTime createdAt;

    @UpdateTimestamp  // ye har update pr automatic update hota rhega date and time
    private LocalDateTime updatedAt;

    public SupportTicket() {}

    //Constructor
//    public SupportTicket(int id, String issue, String status){
//        this.id=id;
//        this.issue=issue;
//        this.status=status;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
