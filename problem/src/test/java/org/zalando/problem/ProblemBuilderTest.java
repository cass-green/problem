package org.zalando.problem;

/*
 * ⁣​
 * Problem
 * ⁣⁣
 * Copyright (C) 2015 Zalando SE
 * ⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ​⁣
 */

import org.junit.Test;

import java.net.URI;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature;
import static org.junit.Assert.assertThat;

public class ProblemBuilderTest {

    private final URI type = URI.create("https://example.org/out-of-stock");

    @Test
    public void shouldCreateProblem() {
        final Problem problem = Problem.builder()
                .withType(type)
                .withTitle("Out of Stock")
                .withStatus(MoreStatus.UNPROCESSABLE_ENTITY)
                .build();

        assertThat(problem, hasFeature("type", Problem::getType, is(type)));
        assertThat(problem, hasFeature("title", Problem::getTitle, is("Out of Stock")));
        assertThat(problem, hasFeature("status", Problem::getStatus, is(MoreStatus.UNPROCESSABLE_ENTITY)));
        assertThat(problem, hasFeature("detail", Problem::getDetail, is(empty())));
        assertThat(problem, hasFeature("instance", Problem::getInstance, is(empty())));
    }

    @Test
    public void shouldCreateProblemWithDetail() {
        final Problem problem = Problem.builder()
                .withType(type)
                .withTitle("Out of Stock")
                .withStatus(MoreStatus.UNPROCESSABLE_ENTITY)
                .withDetail("Item B00027Y5QG is no longer available")
                .build();

        assertThat(problem, hasFeature("detail", Problem::getDetail, is(Optional.of("Item B00027Y5QG is no longer available"))));
    }

    @Test
    public void shouldCreateProblemWithInstance() {
        final Problem problem = Problem.builder()
                .withType(type)
                .withTitle("Out of Stock")
                .withStatus(MoreStatus.UNPROCESSABLE_ENTITY)
                .withInstance(URI.create("http://example.com/"))
                .build();

        assertThat(problem, hasFeature("instance", Problem::getInstance, is(Optional.of(URI.create("http://example.com/")))));
    }

    @Test
    public void shouldCreateProblemWithParameters() {
        final DefaultProblem problem = Problem.builder()
                .withType(type)
                .withTitle("Out of Stock")
                .withStatus(MoreStatus.UNPROCESSABLE_ENTITY)
                .with("foo", "bar")
                .build();

        assertThat(problem.getParameters(), hasEntry("foo", "bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnCustomType() {
        Problem.builder().with("type", "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnCustomTitle() {
        Problem.builder().with("title", "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnCustomStatus() {
        Problem.builder().with("status", "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnCustomDetail() {
        Problem.builder().with("detail", "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnCustomInstance() {
        Problem.builder().with("instance", "foo");
    }

}