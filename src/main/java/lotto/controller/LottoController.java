package lotto.controller;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lotto.exception.ErrorStatus;
import lotto.view.LottoOutput;
import lotto.entity.Lottos;

public class LottoController {

    private final Lottos lottos;
    private final LottoOutput lottoOutput;

    public LottoController(Lottos lottos, LottoOutput output) {
        this.lottos = lottos;
        this.lottoOutput = output;
    }

    public void startLotto() {
        while (true) {
            lottoOutput.requestMoney();
            try {
                String moneyString = Console.readLine();
                Long money = changeStringToMoney(moneyString);
                lottos.buyLottos(money);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        lottoOutput.printAboutLottos(lottos);

        while (true) {
            try {
                lottoOutput.requestWinningNumbers();
                String winningNumbersString = Console.readLine();
                List<Integer> winningNumbers = changeStringToNumberList(winningNumbersString);
                lottos.setWinningNumbers(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        while (true) {
            try {
                lottoOutput.requestBonusNumber();
                String bonusNumberString = Console.readLine();
                Integer bonusNumber = changeStringToNumber(bonusNumberString);
                lottos.setBonusNumber(bonusNumber);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        // to output -> "당첨 통계 ~~"
    }

    private Long changeStringToMoney(String moneyString) throws IllegalArgumentException {
        validateMoney(moneyString);
        return Long.parseLong(moneyString);
    }

    private void validateMoney(String moneyString) throws IllegalArgumentException {
        // 값이 없는 경우
        if (moneyString.isEmpty()) {
            throw new IllegalArgumentException(ErrorStatus.NO_VALUE.getMessage());
        }

        moneyString = moneyString.trim();
        // 숫자가 아닌 입력이 들어온 경우
        if (!Pattern.matches("[\\d]+", moneyString)) {
            throw new IllegalArgumentException(ErrorStatus.INVALID_MONEY_INPUT.getMessage());
        }

        // 너무 큰 값이 들어온 경우
        if (moneyString.length() > 10) {
            throw new IllegalArgumentException(ErrorStatus.MONEY_OUT_OF_RANGE.getMessage());
        }
    }

    private List<Integer> changeStringToNumberList(String numbersString) throws IllegalArgumentException {
        List<Integer> numbers = new ArrayList<>();

        for (String numberString : numbersString.split(",", -1)) {
            validateLottoNumbersString(numberString);
            numbers.add(Integer.parseInt(numberString.trim()));
        }

        return numbers;
    }

    private Integer changeStringToNumber(String numberString) throws IllegalArgumentException {
        validateBonusNumberString(numberString);
        return Integer.parseInt(numberString);
    }

    private void validateLottoNumbersString(String numberString) throws IllegalArgumentException {
        // 값이 없는 경우
        if (numberString.isEmpty()) {
            throw new IllegalArgumentException(ErrorStatus.NO_VALUE.getMessage());
        }

        // 숫자만으로 구성되었는지 확인
        numberString = numberString.trim();
        if (!Pattern.matches("[\\d]+", numberString)) {
            throw new IllegalArgumentException(ErrorStatus.INVALID_LOTTO_INPUT.getMessage());
        }

        // 너무 큰 값이 들어온 경우
        if (numberString.length() > 3) {
            throw new IllegalArgumentException(ErrorStatus.WINNING_NUMBER_TOO_BIG.getMessage());
        }
    }

    private void validateBonusNumberString(String numberString) throws IllegalArgumentException {
        // 값이 없는 경우
        if (numberString.isEmpty()) {
            throw new IllegalArgumentException(ErrorStatus.NO_VALUE.getMessage());
        }

        // 숫자만으로 구성되었는지 확인
        numberString = numberString.trim();
        if (!Pattern.matches("[\\d]+", numberString)) {
            throw new IllegalArgumentException(ErrorStatus.INVALID_BONUS_INPUT.getMessage());
        }

        // 너무 큰 값이 들어온 경우
        if (numberString.length() > 3) {
            throw new IllegalArgumentException(ErrorStatus.BONUS_NUMBER_TOO_BIG.getMessage());
        }
    }
}
