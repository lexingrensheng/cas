package org.apereo.cas.support.saml.services.logout;

import org.apereo.cas.logout.DefaultSingleLogoutRequestContext;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.mock.MockTicketGrantingTicket;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.RegisteredServiceTestUtils;
import org.apereo.cas.support.saml.BaseSamlIdPConfigurationTests;
import org.apereo.cas.support.saml.SamlIdPTestUtils;
import org.apereo.cas.support.saml.web.idp.profile.slo.SamlIdPProfileSingleLogoutMessageCreator;
import org.apereo.cas.support.saml.web.idp.profile.slo.SamlIdPSingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.util.CollectionUtils;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opensaml.saml.common.xml.SAMLConstants;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link SamlIdPProfileSingleLogoutMessageCreatorTests}.
 *
 * @author Misagh Moayyed
 * @since 6.0.0
 */
@Tag("SAML")
public class SamlIdPProfileSingleLogoutMessageCreatorTests extends BaseSamlIdPConfigurationTests {

    @Test
    public void verifyOperation() throws Exception {
        val creator = new SamlIdPProfileSingleLogoutMessageCreator(openSamlConfigBean, servicesManager,
            defaultSamlRegisteredServiceCachingMetadataResolver,
            casProperties.getAuthn().getSamlIdp(),
            samlIdPObjectSigner);

        val logoutRequest = DefaultSingleLogoutRequestContext.builder()
            .logoutUrl(new URL("https://sp.example.org/slo"))
            .registeredService(SamlIdPTestUtils.getSamlRegisteredService())
            .service(RegisteredServiceTestUtils.getService("https://sp.testshib.org/shibboleth-sp"))
            .ticketId("ST-123456789")
            .executionRequest(SingleLogoutExecutionRequest.builder()
                .ticketGrantingTicket(new MockTicketGrantingTicket("casuser"))
                .build())
            .logoutType(RegisteredServiceLogoutType.BACK_CHANNEL)
            .properties(CollectionUtils.wrap(SamlIdPSingleLogoutServiceLogoutUrlBuilder.PROPERTY_NAME_SINGLE_LOGOUT_BINDING,
                SAMLConstants.SAML2_POST_BINDING_URI))
            .build();

        val result = creator.create(logoutRequest);
        assertNotNull(result);
    }
}
