package com.github.aheise.sonar_test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.Test;

/**
 * Created by arvid on 16.07.17.
 */
public class DuplicateFinderTest {
  @Test
  public void shouldBuildTransitiveClosureOfPairs() {
    Set<? extends Set<String>> pairs = Sets.newHashSet(
        Sets.newHashSet("s1", "s2"),
        Sets.newHashSet("s2", "s3"),
        Sets.newHashSet("s1", "s4"),
        Sets.newHashSet("s5", "s6"),
        Sets.newHashSet("s7", "s5"),
        Sets.newHashSet("s8", "s9"));

    final Set<Set<String>> tc = new DuplicateFinder().createTransitiveClosure(pairs);

    Set<? extends Set<String>> expected = Sets.newHashSet(
        Sets.newHashSet("s1", "s2", "s3", "s4"),
        Sets.newHashSet("s5", "s6", "s7"),
        Sets.newHashSet("s8", "s9"));
    assertThat(tc, equalTo(expected));
  }
}