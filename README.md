[![Build Status](https://github.com/mP1/walkingkooka-validation/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-validation/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-validation/badge.svg?branch=master)](https://coveralls.io/repos/github/mP1/walkingkooka-validation?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-validation.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-validation/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-validation.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-validation/alerts/)
![](https://tokei.rs/b1/github/mP1/walkingkooka-validation)
[![J2CL compatible](https://img.shields.io/badge/J2CL-compatible-brightgreen.svg)](https://github.com/mP1/j2cl-central)

A validation framework that supports validating individual fields within a form.

## [Converters](https://github.com/mP1/walkingkooka-convert/blob/master/src/main/java/walkingkooka/convert/Converter.java)

- [has-optional-validator-selector](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterHasOptionalValidatorSelector.java)
- [text-to-form-name](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterTextToFormName.java)
- [text-to-validator-selector](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterTextToValidatorSelector.java)
- [text-to-value-type-name](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterTextToValueTypeName.java)
- [to-validation-checkbox](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterValidaationCheckbox.java)
- [to-validation-choice](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterToValidationChoice.java)
- [to-validation-choice-list](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterValidationChoiceList.java)
- [to-validation-error-list](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/convert/ValidationConverterValidationErrorList.java)

## [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

- [getValidator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/expression/function/ValidationExpressionFunctionGetValidator.java)
- [validationChoiceList](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/expression/function/ValidationExpressionFunctionValidationChoiceList.java)
- [validationErrorIf](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/expression/function/ValidationExpressionFunctionValidationErrorIf.java)
- [validationValue](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/expression/function/ValidationExpressionFunctionValidationValue.java)

## [Validators](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/Validator.java)

Within a spreadsheet, individual cells or a fields within a form can have a [Validator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/Validator.java).
Each [Validator](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/Validator.java) can report zero or more [ValidationError](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ValidationError.java).

The [ValidationError.value](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ValidationError.java) may also contain [ValidationChoiceList](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ValidationChoiceList.java)
which is used by the Spreadsheet to display a drop down of choices.

- [absolute-url](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/AbsoluteUrlValidator.java)
- [collection](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/javex/walkingkooka/validation/ValidatorCollection.java)
- [email-address](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/EmailAddressValidator.java)
- [expression](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/ExpressionValidator.java)
- [non-null](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/NonNullValidator.java)
- [text-length](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/TextLengthValidator.java)
- [text-mask](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/java/walkingkooka/validation/TextMaskValidator.java)
- [validation-choice-list](https://github.com/mP1/walkingkooka-validation/blob/master/src/main/javex/walkingkooka/validation/ValidationChoiceListExpressionValidator.java)