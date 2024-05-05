package com.cts.wishlistservice.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.observation.tck.TestObservationRegistry;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.InheritingConfiguration;
import org.modelmapper.spi.NameTokenizer;
import org.modelmapper.spi.ValueWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AppConfigJunitTest {
    @Autowired
    private AppConfig appConfig;

    @MockBean
    private ObservationRegistry observationRegistry;

    @MockBean
    private ObservedAspect observedAspect;

    /**
     * Method under test: {@link AppConfig#modelMapper()}
     */
    @Test
    void testModelMapper() {
        // Arrange, Act and Assert
        Configuration configuration = appConfig.modelMapper().getConfiguration();
        List<ValueWriter<?>> expectedValueWriters = configuration.getValueWriters();
        assertSame(expectedValueWriters, ((InheritingConfiguration) configuration).valueMutateStore.getValueWriters());
    }

    /**
     * Method under test: {@link AppConfig#observedAspect(ObservationRegistry)}
     */
    @Test
    void testObservedAspect() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AppConfig appConfig = new AppConfig();

        // Act
        appConfig.observedAspect(TestObservationRegistry.create());

        // Assert
        ModelMapper modelMapperResult = appConfig.modelMapper();
        Configuration configuration = modelMapperResult.getConfiguration();
        assertEquals(1, configuration.getValueReaders().size());
        List<ValueWriter<?>> valueWriters = configuration.getValueWriters();
        assertEquals(1, valueWriters.size());
        assertEquals(11, configuration.getConverters().size());
        assertEquals(Configuration.AccessLevel.PUBLIC, configuration.getFieldAccessLevel());
        assertEquals(Configuration.AccessLevel.PUBLIC, configuration.getMethodAccessLevel());
        assertFalse(configuration.isDeepCopyEnabled());
        assertFalse(valueWriters.get(0).isResolveMembersSupport());
        assertTrue(modelMapperResult.getTypeMaps().isEmpty());
        assertTrue(configuration.isCollectionsMergeEnabled());
        NameTokenizer expectedSourceNameTokenizer = configuration.getDestinationNameTokenizer();
        assertSame(expectedSourceNameTokenizer, configuration.getSourceNameTokenizer());
    }

    /**
     * Method under test: {@link AppConfig#observedAspect(ObservationRegistry)}
     */
    @Test
    void testObservedAspect2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AppConfig appConfig = new AppConfig();
        ObservationRegistry observationRegistry = mock(ObservationRegistry.class);
        when(observationRegistry.observationConfig()).thenReturn(new ObservationRegistry.ObservationConfig());

        // Act
        appConfig.observedAspect(observationRegistry);

        // Assert
        verify(observationRegistry).observationConfig();
    }

    /**
     * Method under test: {@link AppConfig#observedAspect(ObservationRegistry)}
     */
    @Test
    void testObservedAspect3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AppConfig appConfig = new AppConfig();
        ObservationRegistry.ObservationConfig observationConfig = mock(ObservationRegistry.ObservationConfig.class);
        // Assuming ObservationHandler is a generic class with type parameter T
        when(observationConfig.observationHandler(Mockito.any(ObservationHandler.class)))
                .thenReturn(new ObservationRegistry.ObservationConfig());

        ObservationRegistry observationRegistry = mock(ObservationRegistry.class);
        when(observationRegistry.observationConfig()).thenReturn(observationConfig);

        // Act
        appConfig.observedAspect(observationRegistry);

        // Assert
        verify(observationRegistry).observationConfig();
        verify(observationConfig).observationHandler(isA(ObservationHandler.class));
    }
}
