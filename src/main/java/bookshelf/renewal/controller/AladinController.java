package bookshelf.renewal.controller;

import bookshelf.renewal.dto.BookDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/aladin/search")
@RequiredArgsConstructor
public class AladinController {


    @Value("${aladin.api.key}")
    private String apiKey;

    @Value("${aladin.api.url}")
    private String apiUrl;

    ObjectMapper mapper = new ObjectMapper();


    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam String query, Pageable pageable){
        int maxResults = 24;
        String url = apiUrl + "/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey
                + "&Query=" + query
                + "&MaxResults=" + maxResults + "&start=" + 1 + "&Cover=Big&SearchTarget=Book&output=JS&Version=20131101";

        RestTemplate restTemplate = new RestTemplate();
        try{
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            List<BookDto> filteredBooks = filterValidBooks(response.get("item"));
            return ResponseEntity.ok(new PageImpl<>(filteredBooks, pageable, filteredBooks.size()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from api");
        }

    }

    @GetMapping("/limit")
    public ResponseEntity<?> searchBooksLimit(@RequestParam String query, @RequestParam int limit){
        int maxResults = limit;
        String url = apiUrl + "/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey
                + "&Query=" + query
                + "&MaxResults=" + maxResults + "&start=" + 1 + "&Cover=Big&SearchTarget=Book&output=JS&Version=20131101";

        RestTemplate restTemplate = new RestTemplate();
        try{
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            List<BookDto> filteredBooks = filterValidBooks(response.get("item"));
            return ResponseEntity.ok(filteredBooks);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from api");
        }

    }

    @GetMapping("/isbn")
    public ResponseEntity<?> searchByIsbn(@RequestParam String isbn){
        log.info("isbn = {}", isbn);
        String url = apiUrl + "/ttb/api/ItemLookUp.aspx?ttbkey=" + apiKey
                + "&itemIdType=ISBN&ItemId=" + isbn + "&output=JS&Version=20131101&OptResult=ebookList,usedList,reviewList";

        RestTemplate restTemplate = new RestTemplate();
        try{
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            List<BookDto> filteredBooks = filterValidBooks(response.get("item"));
            return ResponseEntity.ok(filteredBooks);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from api");
        }

    }

    private List<BookDto> filterValidBooks(JsonNode searchedBooks) {
        List<BookDto> bookDtos = new ArrayList<>();

        for (JsonNode book : searchedBooks) {
            BookDto bookDto = BookDto.getBookDto(book);

            if (bookDto.getPublisher().equals("알라딘 이벤트") || bookDto.getAuthor().equals("") || bookDto.getIsbn().equals("")) {
                continue;
            }
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

}

