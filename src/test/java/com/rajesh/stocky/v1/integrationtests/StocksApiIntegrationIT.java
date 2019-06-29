package com.rajesh.stocky.v1.integrationtests;

import com.rajesh.stocky.v1.StockyApplication;
import com.rajesh.stocky.v1.swagger.model.CreateStockRequestDTO;
import com.rajesh.stocky.v1.swagger.model.StockDetailResponse;
import com.rajesh.stocky.v1.swagger.model.StockListResponse;
import com.rajesh.stocky.v1.swagger.model.UpdateStockRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockyApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
public class StocksApiIntegrationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStockSuccess() {
        CreateStockRequestDTO createStockRequest = new CreateStockRequestDTO();
        createStockRequest.setCurrentPrice(4.9);
        createStockRequest.setStockName("payconiqStockCreateTest");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity<CreateStockRequestDTO> request = new HttpEntity<>(createStockRequest, headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.postForEntity(createURLWithPort("/api/stocks/"), request, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(4.9);
        assertThat(response.getBody().getStockName()).isEqualTo("payconiqStockCreateTest");
    }

    @Test
    public void testCreateStockBadRequest() {
        CreateStockRequestDTO createStockRequest = new CreateStockRequestDTO();
        createStockRequest.setStockName("payconiqStockCreateTest");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity<CreateStockRequestDTO> request = new HttpEntity<>(createStockRequest, headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.postForEntity(createURLWithPort("/api/stocks/"), request, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetStockByIdWhenExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.exchange(createURLWithPort("/api/stocks/942876"), HttpMethod.GET, entity, StockDetailResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(82.3);
        assertThat(response.getBody().getStockName()).isEqualTo("payconiqStockGetTest");
    }

    @Test
    public void testGetStockByIdWhenNotExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.exchange(createURLWithPort("/api/stocks/23421"), HttpMethod.GET, entity, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteStockByIdWhenExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort("/api/stocks/716833"), HttpMethod.DELETE, entity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteStockByIdWhenNotExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort("/api/stocks/43532"), HttpMethod.DELETE, entity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStockListSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/stocks/"))
                .queryParam("page", 0)
                .queryParam("size", 40);

        ResponseEntity<StockListResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, StockListResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetStockListPageOutOfBound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/stocks/"))
                .queryParam("page", 5)
                .queryParam("size", 40);

        ResponseEntity<StockListResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, StockListResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStockListInvalidPageSize() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/api/stocks/"))
                .queryParam("page", 0)
                .queryParam("size", 260);
        ResponseEntity<StockListResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, StockListResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateStockBadRequest() {
        UpdateStockRequestDTO updateStockRequest = new UpdateStockRequestDTO();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity<UpdateStockRequestDTO> request = new HttpEntity<>(updateStockRequest, headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.exchange(createURLWithPort("/api/stocks/678223"), HttpMethod.PUT, request, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateStockWhenExists() {
        UpdateStockRequestDTO updateStockRequest = new UpdateStockRequestDTO();
        updateStockRequest.setCurrentPrice(105.6);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity<UpdateStockRequestDTO> request = new HttpEntity<>(updateStockRequest, headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.exchange(createURLWithPort("/api/stocks/678223"), HttpMethod.PUT, request, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStockName()).isEqualTo("payconiqStockUpdateTest");
        assertThat(response.getBody().getCurrentPrice()).isEqualTo(105.6);
    }

    @Test
    public void testUpdateStockWhenNotExists() {
        UpdateStockRequestDTO updateStockRequest = new UpdateStockRequestDTO();
        updateStockRequest.setCurrentPrice(105.6);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c3RvY2t5OnN0b2NreQ==");
        HttpEntity<UpdateStockRequestDTO> request = new HttpEntity<>(updateStockRequest, headers);

        ResponseEntity<StockDetailResponse> response = restTemplate.exchange(createURLWithPort("/api/stocks/436622"), HttpMethod.PUT, request, StockDetailResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}