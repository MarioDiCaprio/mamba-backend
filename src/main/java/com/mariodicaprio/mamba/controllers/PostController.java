package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import com.mariodicaprio.mamba.responses.PageResponse;
import com.mariodicaprio.mamba.responses.PostResponse;
import com.mariodicaprio.mamba.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Transactional
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    ///////////////////////////////////////////////////////////////

    @GetMapping("/all")
    @Operation(description = "Fetches a page of posts sorted by newest to oldest. The page contains up to 15 posts.")
    @ApiResponse(responseCode = "200", description = "The requested page with up to 15 posts sorted from newest to oldest")
    public PageResponse<PostResponse> all(
            @RequestParam(required = false, defaultValue = "1")
            @Parameter(description = "The page's index (one-based)")
            int page
    ) {
        Page<PostResponse> tmp = postService.all(page).map(PostResponse::new);
        return new PageResponse<>(tmp);
    }

    @PostMapping("/create")
    @Operation(description = "Creates a post with the given data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post was created successfully"),
            @ApiResponse(responseCode = "404", description = "Post owner does not exist")
    })
    public void create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The creation request")
            PostCreationRequest request
    ) throws UserNotFoundException {
        postService.createPost(request);
    }

}
