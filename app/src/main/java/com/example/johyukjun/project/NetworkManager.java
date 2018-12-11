package com.example.johyukjun.project;

public class NetworkManager {

    private static NetworkManager one;
    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if(one == null) {
            one = new NetworkManager();
        }
        return one;
    }

    public static void parsePacket(NetType pType)
    {
        switch(pType)
        {
            case ERROR:
                //  에러
                break;
            case LOGIN:
                // 로그인 성공시 화면 넘기는 함수
                //
                break;
            case LOGOUT:
                // 로그아웃 성공시 화면 넘기는 함수
                break;
            case REG_USR:
                // 유저 등록 성공시 화면 넘기는 함수
                break;
            case REG_DEV:
                // 디바이스 등록 성공시 화면 넘기는 함수
                break;
            case ENROLL_DEV:
                // 클라에서 사용하지 않음
                break;
            case RMV_USR_DEV:
                // 디바이스 제거 성공시 제거하는 함수
                break;
            case SEND_DEV_LIST:
                // 디바이스 리스트 불러오는 경우
                break;
            case SEL_DEV:
                // 디바이스를 선택하는 경우 UI정보 받음
                break;
            case CTRL_DEV:
                // 디바이스 조절
                break;
            case CHNG_USER_INFO:
                // 유저 정보 변경
                break;
            default:
                break;
        }
    }
}
