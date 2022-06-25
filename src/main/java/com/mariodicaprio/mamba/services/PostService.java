package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.Tag;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.requests.CreatePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    ///////////////////////////////////////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<Post> postAll(int page) {
        Sort sort = Sort.by("dateCreated").descending();
        Pageable pageable = PageRequest.of(page - 1, 15, sort);
        return postRepository.findAll(pageable).toList();
    }

    @Transactional(readOnly = true)
    public Post postById(UUID postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Post> postsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Transactional
    public Post createPost(CreatePostRequest request) {
        List<Tag> tags =
                (request.getTagNames() == null)? new ArrayList<>() :
                        request
                                .getTagNames()
                                .stream()
                                .map(tagName -> tagService.tagByName(tagName))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toCollection(ArrayList::new));
        User owner = (request.getOwnerId() == null)? null : userService.userById(request.getOwnerId());
        Post reposts = (request.getRepostsId() == null)? null : postById(request.getRepostsId());

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        post.setMedia(request.getMedia());
        post.setOwner(owner);
        post.setTags(tags);
        post.setReposts(reposts);
        post.setType(request.getType());
        postRepository.save(post);

        return post;
    }

}
