package io.jmlim.mongoex.demo.service;

import io.jmlim.mongoex.demo.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationSampleService {

    private final MongoTemplate mongoTemplate;

    public List<Map> groupCount() {

        //group
        GroupOperation groupOperation = Aggregation.group("mainCatId")
                .count().as("count");

        //aggrgation
        AggregationResults<Map> aggregate =
                this.mongoTemplate.aggregate(newAggregation(groupOperation), Product.class, Map.class);

        return aggregate.getMappedResults();
    }

}
