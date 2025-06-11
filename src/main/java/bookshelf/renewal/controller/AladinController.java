package bookshelf.renewal.controller;

import bookshelf.renewal.dto.BookDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/aladin")
@RequiredArgsConstructor
public class AladinController {


    @Value("${aladin.api.key}")
    private String apiKey;

    @Value("${aladin.api.url}")
    private String apiUrl;

    ObjectMapper mapper = new ObjectMapper();


    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String query, @RequestParam int page){
        int maxResults = 40;
        String url = apiUrl + "/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey
                + "&Query=" + query
                + "&MaxResults=" + maxResults + "&start=" + page + "&Cover=Big&SearchTarget=Book&output=JS&Version=20131101";

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
            BookDto bookDto = getBookDto(book);

            if (bookDto.getPublisher().equals("알라딘 이벤트") || bookDto.getAuthor().equals("") || bookDto.getIsbn().equals("")) {
                continue;
            }

            bookDtos.add(bookDto);

        }

        return bookDtos;
    }

    private BookDto getBookDto(JsonNode book){
        String title = book.get("title").asText();
        String author = book.get("author").asText();
        String publisher = book.get("publisher").asText();
        String isbn = book.get("isbn").asText();
        String seriesName = book.has("seriesInfo") ? book.get("seriesInfo").get("seriesName").asText() : "";
        String cover = book.get("cover").asText();
        String categoryName = book.get("categoryName").asText();
        String link = book.get("link").asText();
        String pubDate = book.get("pubDate").asText();
        return new BookDto(title, author, publisher, isbn, seriesName, cover, categoryName, link, LocalDate.parse(pubDate));
    }
}

