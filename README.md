[![Build Status](https://github.com/mP1/walkingkooka-validation/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-validation/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-validation/badge.svg?branch=master)](https://coveralls.io/repos/github/mP1/walkingkooka-validation?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-validation.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-validation/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-validation.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-validation/alerts/)
![](https://tokei.rs/b1/github/mP1/walkingkooka-validation)
[![J2CL compatible](https://img.shields.io/badge/J2CL-compatible-brightgreen.svg)](https://github.com/mP1/j2cl-central)

A validation framework that supports validating individual fields within a form.

# [Converters](https://github.com/mP1/walkingkooka-convert/blob/master/src/main/java/walkingkooka/convert/Converter.java)

- [HasOptionalValidatorSelector](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/HasOptionalValidatorSelectorConverter.java)
- [TextToFormNameConverter](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/TextToFormNameConverter.java)
- [TextToValidationValueTypeNameConverter](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/TextToValidationValueTypeNameConverter.java)
- [TextToValidatorSelectorConverter](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/TextToValidatorSelectorConverter.java)
- [ValidationChoiceListConverter](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationChoiceListConverter.java)
- [ValidationErrorListConverter](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationErrorListConverter.java)

# [Validators](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/Validator.java)

Within a spreadsheet, individual cells or a fields within a form can have a validator. Each validator can report zero
or more [ValidationError](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ValidationError.java).

- [ExpressionValidator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ExpressionValidator.java)
- [NonNullValidator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/NonNullValidator.java)
- [TextLengthValidator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/TextLengthValidator.java)
- [ValidatorCollection](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ValidatorCollection.java)