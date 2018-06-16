import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class VocabulariesCreateTable {

    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:5555", "us-west-2"))
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Vocabularies";
        long readCapacityUnits = 10L;
        long writeCapacityUnits = 10L;
        String partitionKeyName = "id";
        String partitionKeyType = "S";
        String sortKeyName = "deckId";
        String sortKeyType = "S";
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                    Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
//        try {
//
//            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
//            keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
//            // key
//
//            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
//            attributeDefinitions
//                    .add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));
//
//            if (sortKeyName != null) {
//                keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
//                // key
//                attributeDefinitions
//                        .add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
//            }
//
//            CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
//                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
//                            .withWriteCapacityUnits(writeCapacityUnits));
//
//            // If this is the Reply table, define a local secondary index
////            if (replyTableName.equals(tableName)) {
////
//                attributeDefinitions
//                        .add(new AttributeDefinition().withAttributeName("nextLearnedAt").withAttributeType("S"));
//
//                ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
//                localSecondaryIndexes.add(new LocalSecondaryIndex().withIndexName("nextLearnedAt-Index")
//                        .withKeySchema(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH), // Partition
//                                // key
//                                new KeySchemaElement().withAttributeName("nextLearnedAt").withKeyType(KeyType.RANGE)) // Sort
//                        // key
//                        .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)));
//
//                request.setLocalSecondaryIndexes(localSecondaryIndexes);
////            }
//
//            request.setAttributeDefinitions(attributeDefinitions);
//
//            System.out.println("Issuing CreateTable request for " + tableName);
//            Table table = dynamoDB.createTable(request);
//            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
//            table.waitForActive();
//
//        }
//        catch (Exception e) {
//            System.err.println("CreateTable request failed for " + tableName);
//            System.err.println(e.getMessage());
//        }
    }
}
