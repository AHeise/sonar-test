package com.github.aheise.sonar_test;

import java.time.LocalDate;
import lombok.Data;

/**
 * Created by arvid on 16.07.17.
 */
@Data
public class Person {
  private String firstName, lastName;
  private Address privateAddress, workAddress;
  private LocalDate birthDay;
}
