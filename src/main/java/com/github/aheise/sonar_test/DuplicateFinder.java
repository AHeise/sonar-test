package com.github.aheise.sonar_test;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.jooq.lambda.Seq;

/**
 * Created by arvid on 16.07.17.
 */
public class DuplicateFinder {

  public <T> Set<Set<T>> findDuplicates(Collection<T> elements,
      BiPredicate<T, T> duplicatePredicate, Function<T, ?>... blockings) {
    return createTransitiveClosure(
        Seq.of(blockings).flatMap(blocking ->
            Seq.seq(elements).
                grouped(blocking).
                flatMap(block -> findDuplicatesNoBlocking(block.v2().toList(), duplicatePredicate)
                    .stream())));
  }

  public <T> Set<Set<T>> findDuplicatesNoBlocking(List<T> elements,
      BiPredicate<T, T> duplicatePredicate) {
        return createTransitiveClosure(Seq.seq(elements).innerJoin(elements,
        (p1, p2) -> System.identityHashCode(p1) < System.identityHashCode(p2) &&
            duplicatePredicate.test(p1, p2)).
        map(pair -> Sets.newHashSet(pair.v1(), pair.v2())));
  }

    <T> Set<Set<T>> createTransitiveClosure(Iterable<? extends Iterable<T>> partialClosures) {
      Map<T, Set<T>> clusters = new IdentityHashMap<>();

      for (Iterable<T> partialClosure : partialClosures) {
      final Set<T> mergedCluster = Seq.seq(partialClosure)
          .map(clusters::get)
              .filter(Objects::nonNull)
          .concat(Sets.newHashSet(partialClosure))
          .distinct(System::identityHashCode)
          .reduce((s1, s2) -> {
            s1.addAll(s2);
            return s1;
          })
          .get();

      Seq.seq(mergedCluster).forEach(d -> clusters.put(d, mergedCluster));
    }

    return Sets.newHashSet(clusters.values());
  }
}
