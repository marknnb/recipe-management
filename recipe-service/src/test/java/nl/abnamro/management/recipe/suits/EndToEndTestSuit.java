package nl.abnamro.management.recipe.suits;

import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectPackages("nl.abnamro.management.recipe.end_to_end")
@Disabled
public class EndToEndTestSuit {}
