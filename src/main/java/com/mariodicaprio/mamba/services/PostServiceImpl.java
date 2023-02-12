package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Media;
import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    ///////////////////////////////////////////////////////////

    @Override
    public Page<Post> all(int page) {
        log.info("Fetching all posts on page " + page);
        Sort sort = Sort.by("dateCreated").descending();
        Pageable pageable = PageRequest.of(page - 1, 15, sort);
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void createPost(PostCreationRequest request) throws UserNotFoundException {
        log.info("Initiating post creation");

        if (request.getUsername() == null) {
            log.error("No username found for post creation request (username=null)");
            throw new UserNotFoundException();
        }

        User owner = userService.findByUsername(request.getUsername());

        Media media = null;
        if (request.getMedia() != null) {
            media = new Media();
            media.setData(request.getMedia().getData());
            media.setType(request.getMedia().getType());
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setMedia(media);
        post.setOwner(owner);

        log.info("Finishing post creation");
        postRepository.save(post);
    }

}
