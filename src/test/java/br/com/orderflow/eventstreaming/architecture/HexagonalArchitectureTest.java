package br.com.orderflow.eventstreaming.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

/**
 * Architecture tests to ensure hexagonal boundaries between layers.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
class HexagonalArchitectureTest {

        private static final String BASE_PACKAGE = "br.com.orderflow.eventstreaming";

        /**
         * Verifies that the domain does not directly depend on Spring.
         * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
         * Architectures.
         */
        @Test
        void domainShouldNotDependOnSpring() {
                ArchRule rule = noClasses()
                                .that()
                                .resideInAPackage("..domain..")
                                .should()
                                .dependOnClassesThat()
                                .resideInAPackage("org.springframework..");

                rule.check(new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                                .importPackages(BASE_PACKAGE));
        }

        /**
         * Verifies that the application does not directly depend on infrastructure.
         * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
         * Architectures.
         */
        @Test
        void applicationShouldNotDependOnInfra() {
                ArchRule rule = noClasses()
                                .that()
                                .resideInAPackage("..application..")
                                .should()
                                .dependOnClassesThat()
                                .resideInAPackage("..infra..");

                rule.check(new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                                .importPackages(BASE_PACKAGE));
        }
}
