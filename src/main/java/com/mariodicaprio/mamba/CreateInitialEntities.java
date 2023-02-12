package com.mariodicaprio.mamba;


import com.mariodicaprio.mamba.entities.Media;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import com.mariodicaprio.mamba.requests.SignupRequest;
import com.mariodicaprio.mamba.services.PostService;
import com.mariodicaprio.mamba.services.SignupService;
import com.mariodicaprio.mamba.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;


@Component
@Profile("!test")
@Log4j2 @RequiredArgsConstructor
public class CreateInitialEntities implements CommandLineRunner {

    private final SignupService signupService;

    private final PostService postService;

    private final UserService userService;

    ////////////////////////////////////////////////////////////////////////////

    private Media mediaFromClasspath(String path, String mimeType) throws Exception {
        var file = new ClassPathResource(path).getFile();
        var content = Files.readAllBytes(file.toPath());
        var media = new Media();
        media.setData(content);
        media.setType(mimeType);
        return media;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Registering initial users");

        signupService.signup(new SignupRequest("user", "user@gmail.com", "password"));
        User user = userService.findByUsername("user");

        log.info("Creating initial posts");

        var elephantsMedia = mediaFromClasspath("img/elephants.png", "img/png");
        postService.createPost(new PostCreationRequest(
                "Family of Elephants",
            """
                    Ever-since I was little, I have always loved elephants.
                    Over here, you can see a family enjoying a walk back
                    home!
                    """,
                    new PostCreationRequest.PostCreationRequestMedia(elephantsMedia.getData(), elephantsMedia.getType()),
                    user.getUsername()
        ));
    }

}
