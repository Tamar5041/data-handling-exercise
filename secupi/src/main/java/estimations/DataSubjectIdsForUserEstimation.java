package estimations;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import data.ResultEntry;
import net.agkn.hll.HLL;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.*;
import java.util.stream.Collectors;

public class DataSubjectIdsForUserEstimation {

    public List<ResultEntry> estimate(List<ConsumerRecord<Integer, Integer>> records) {
        final Map<Integer, List<ConsumerRecord<Integer, Integer>>> groupingMap = records.stream()
                .collect(Collectors.groupingBy(ConsumerRecord::key));
        return groupingMap.entrySet().stream()
                .map(this::hyperLogLog)
                .collect(Collectors.toList());
    }

    private ResultEntry hyperLogLog(Map.Entry<Integer, List<ConsumerRecord<Integer, Integer>>> groupingEntry) {
        final HashFunction hashFunction = Hashing.murmur3_128();
        final HLL hll = new HLL(14, 8);
        groupingEntry.getValue().forEach(element -> {{
            long hashedValue = hashFunction.newHasher().putLong(element.value()).hash().asLong();
            hll.addRaw(hashedValue);
            }
        });
        long uniqueDataSubjectIds  = hll.cardinality();
        return new ResultEntry(groupingEntry.getKey(), uniqueDataSubjectIds);
    }
}