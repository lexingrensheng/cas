package org.apereo.cas.adaptors.yubikey.web.flow;

import org.apereo.cas.adaptors.yubikey.AcceptAllYubiKeyAccountValidator;
import org.apereo.cas.adaptors.yubikey.registry.OpenYubiKeyAccountRegistry;
import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.support.WebUtils;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.test.MockRequestContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link YubiKeyAccountSaveRegistrationActionTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@Tag("WebflowActions")
public class YubiKeyAccountSaveRegistrationActionTests {
    @Test
    public void verifyActionSuccess() throws Exception {
        val context = new MockRequestContext();
        val request = new MockHttpServletRequest();
        request.addParameter(YubiKeyAccountSaveRegistrationAction.PARAMETER_NAME_TOKEN, "yubikeyToken");
        request.addParameter(YubiKeyAccountSaveRegistrationAction.PARAMETER_NAME_ACCOUNT, UUID.randomUUID().toString());
        context.setExternalContext(new ServletExternalContext(new MockServletContext(), request, new MockHttpServletResponse()));

        WebUtils.putAuthentication(CoreAuthenticationTestUtils.getAuthentication(), context);
        val action = new YubiKeyAccountSaveRegistrationAction(new OpenYubiKeyAccountRegistry(new AcceptAllYubiKeyAccountValidator()));
        assertEquals(CasWebflowConstants.TRANSITION_ID_SUCCESS, action.execute(context).getId());
    }
}
