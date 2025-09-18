package com.jyjun.simplemarket2.domain.auth.usecase;

import com.jyjun.simplemarket2.application.auth.dto.ReissueReq;

public interface AuthUseCase {

    String reissueAccessToken(ReissueReq req);

}
