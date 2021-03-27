package ru.vtb.msa.integration.hermes.subscription.exception

import ru.vtb.msa.integration.hermes.subscription.util.HERMES_NOT_ANSWER_MESSAGE

class HermesNotAnswerException : RuntimeException(HERMES_NOT_ANSWER_MESSAGE)