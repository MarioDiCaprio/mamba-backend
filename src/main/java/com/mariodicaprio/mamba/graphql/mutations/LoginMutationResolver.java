package com.mariodicaprio.mamba.graphql.mutations;


import com.mariodicaprio.mamba.requests.LoginRequest;
import com.mariodicaprio.mamba.responses.LoginResponse;
import com.mariodicaprio.mamba.services.LoginService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * This class resolves all GraphQL mutations associated with the
 * {@link LoginService} service.
 */
@Service
@SuppressWarnings("unused")
public class LoginMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private LoginService loginService;

    /////////////////////////////////////////////////////////////////////////////////

    public LoginResponse login(LoginRequest request) {
        return loginService.login(request);
    }

}
