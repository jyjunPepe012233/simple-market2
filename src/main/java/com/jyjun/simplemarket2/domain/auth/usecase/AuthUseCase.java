package com.jyjun.simplemarket2.domain.auth.usecase;

import com.jyjun.simplemarket2.application.auth.dto.ReissueReq;
import com.jyjun.simplemarket2.application.auth.dto.ReissueRes;

public interface AuthUseCase {

    ReissueRes reissue(ReissueReq req);

}
