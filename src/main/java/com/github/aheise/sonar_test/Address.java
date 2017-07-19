package com.github.aheise.sonar_test;

import lombok.Builder;
import lombok.Value;

/**
 * Created by arvid on 16.07.17.
 */
@Value
@Builder
public class Address {
  private String street, zipCode, city;
}
