package com.example.test_appweer2.nfc;

public class RFCommands {

    /***************************************************************************************     * GENERAL COMMANDS     ***************************************************************************************/
    public static final byte[] cmd_EventDetection_3D = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x3D,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
    };

    public static final byte[] cmd_EventDetection_A8  = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA8,
            (byte) 0x04,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
    };

    public static final byte[] cmd_AUTO_STANDBY_MODE_EN  = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x08,
            (byte) 0x09,
            (byte) 0xFF,
            (byte) 0x00,
    };

    public static final byte[] cmd_EnableSRAM  = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA1,
            (byte) 0x00,
            (byte) 0x0B,
    };
    public static final byte[] cmd_ReadSRAM = new byte[]{
            (byte) 0x02,
            (byte) 0xD2,
            (byte) 0x04,
            (byte) 0x00,
            (byte) 0x3F,
    };

    public static byte[] cmd_WriteSRAM  = new byte[]{
            (byte) 0x12,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x01,
            (byte) 0x00,
            (byte)'@',
            (byte)'#',
            (byte)'R',
            (byte)0xd

    };
    public static byte[] cmd_select = new byte[]{
            (byte) 0x20,
            (byte) 0x25,
            0, 0, 0, 0, 0, 0, 0, 0,
    };
    public static final byte[] cmd_readTagConfig = new byte[]{
            (byte) 0x02,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x01
    };
    public static byte[] cmd_ReadCodice = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x04,
            (byte) 0x3f,
            (byte) '@',
            (byte) '#',
            (byte) 'R',
            (byte) 0xd

    };
    public static byte[] cmd_StartI2CRead = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x3F,
            (byte) 0x00,
            (byte) 0xFF,
            (byte) 0xFF,
            (byte) 0xFF,
            (byte) 0xd
    };
    public static byte[] cmd_Seriale = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x09,
            (byte) 0x01,
            (byte) '@',
            (byte) '#',
            (byte) 'W',
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) '#',
            (byte) 0xd,
    };

    public static byte[] cmd_WriteDateStart = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x04,
            (byte) 0x02,
            (byte) '@',
            (byte) '#',
            (byte) 'D',
            (byte) '1',
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte)'#',
            (byte) 0xd,

    };
    public static byte[] cmd_WriteDateEnd = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x06,
            (byte) 0x02,
            (byte) '@',
            (byte) '#',
            (byte) 'D',
            (byte) '2',
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte)'#',
            (byte) 0xd,

    };

    public static final byte[] cmd_readTagConfigReg = new byte[]{
            (byte) 0x02,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x01    };

    public static final byte[] cmd_readSRAMprot = new byte[]{
            (byte) 0x02,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0x3F,
            (byte) 0x01    };

    public static final byte[] cmd_readTagStatus = new byte[]{
            (byte) 0x02,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0xA0,
            (byte) 0x06
    };

    public static final byte[] cmd_writeTagConfig = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37    };

    public static final byte[] cmd_writeTagConfigTest = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x00,
            (byte) 0x02,
            (byte) 0x0F,
            (byte) 0x00    };

    public static final byte[] cmd_writeTagConfigSession = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA1    };

    public static final byte[] cmd_activateI2CSlave = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x00,
            (byte) 0x03,
            (byte) 0x0F,
            (byte) 0x00    };

    public static final byte[] cmd_activateI2CMaster = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x00,
            (byte) 0x12,
            (byte) 0x0F,
            (byte) 0x00    };

    public static final byte[] cmd_activateGPIOPWM = new byte[]{
            (byte) 0x02,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x00,
            (byte) 0x22,
            (byte) 0x0F,
            (byte) 0x00    };

    public static final byte[] cmd_readGPIOPWMConfig = new byte[]{
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0xA3,
            (byte) 0x01    };

    public static final byte[] cmd_writeBlock = new byte[]{
            (byte) 0x02,
            (byte) 0x21,
            (byte) 0x00,
            (byte) 0xAA,
            (byte) 0xAB,
            (byte) 0xAC,
            (byte) 0xAD    };

    public static final byte[] cmd_readBlock = new byte[]{
            (byte) 0x12,
            (byte) 0x20,
            (byte) 0x00    };

    public static final byte[] cmd_activateNormalMode = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x37,
            (byte) 0x00,
            (byte) 0x02,
            (byte) 0x0F,
            (byte) 0x00    };

    public static final byte[] cmd_readSRAMFast = new byte[]{
            (byte) 0x12,
            (byte) 0xD2,
            (byte) 0x04,
            (byte) 0x01,
            (byte) 0x3F    };

    /***************************************************************************************     * GPIO COMMANDS     ***************************************************************************************/
    public static final byte[] cmd_writeGPIOConfig = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0x39,
            (byte) 0x08,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00    };

    public static final byte[] cmd_writeGPIOSession = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA3,
            (byte) 0x08,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00    };

    public static final byte[] cmd_gpioSetSessionOutput = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA3,
            (byte) 0x48,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00    };

    public static final byte[] cmd_gpioClearSessionOutput = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA3,
            (byte) 0x08,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00    };

    /***************************************************************************************     * PWM COMMANDS     ***************************************************************************************/    public static final byte[] cmd_readPWM0Reg = new byte[]{
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0xA4,
            (byte) 0x01    };

    public static final byte[] cmd_readPWM1Reg = new byte[]{
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0xA5,
            (byte) 0x01    };

    public static final byte[] cmd_writePWMSession = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA3    };

    public static final byte[] cmd_writePWM0Reg = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA4    };

    public static final byte[] cmd_writePWM1Reg = new byte[]{
            (byte) 0x12,
            (byte) 0xC1,
            (byte) 0x04,
            (byte) 0xA5    };

    /***************************************************************************************     * PASS-THROUGH COMMANDS     ***************************************************************************************/    public static final byte[] cmd_writeSRAM = new byte[]{
            (byte) 0x02,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x04,
            (byte) 0x3B,
    };

    /***************************************************************************************     * I2C MASTER COMMANDS     ***************************************************************************************/    public static final byte[] cmd_readI2CMasterCommand = new byte[]{
            (byte) 0x12, // FLAGS, CHECK ISO/IEC 15693            (byte) 0xD5, // READ I2C            (byte) 0x04,
            (byte) 0x1F, //Don't generate Stop condition                         //I2C address = 0x1F            (byte) 0x00
     };

    public static final byte[] cmd_GetTempI2CMasterCommand = new byte[]{
            (byte) 0x12, // FLAGS, CHECK ISO/IEC 15693            (byte) 0xD4, // WRITE I2C            (byte) 0x04,
            (byte) 0x9F, //Generate Stop condition                         //I2C address = Stop condition bit | 0x1F = 0x9F            (byte) 0x00,
            (byte) 0x51  // Temperature register in combosensor
     };

    public static final byte[] cmd_GetXaccMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x01    };

    public static final byte[] cmd_GetYaccMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x03    };

    public static final byte[] cmd_GetZaccMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x05    };

    public static final byte[] cmd_GetXmagMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x33    };

    public static final byte[] cmd_GetYmagMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x35    };

    public static final byte[] cmd_GetZmagMSBI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x9F,
            (byte) 0x00,
            (byte) 0x37    };

    public static final byte[] cmd_setHybridModeI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x1F,
            (byte) 0x01,
            (byte) 0x5B,
            (byte) 0x1F    };

    public static final byte[] cmd_setStandByModeI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x1F,
            (byte) 0x01,
            (byte) 0x2A,
            (byte) 0x00    };

    public static final byte[] cmd_setControlReg2I2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x1F,
            (byte) 0x01,
            (byte) 0x5C,
            (byte) 0x20    };

    public static final byte[] cmd_setActiveModeI2CMasterCommand = new byte[]{
            (byte) 0x12,
            (byte) 0xD4,
            (byte) 0x04,
            (byte) 0x1F,
            (byte) 0x01,
            (byte) 0x2A,
            (byte) 0x0D    };

    public static final byte[] cmd_i2cMasterConfigStatus = new byte[]{
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0xAD,
            (byte) 0x01    };

    public static final byte[] cmd_readSRAMI2CMaster = new byte[]{
            (byte) 0x12,
            (byte) 0xD2,
            (byte) 0x04,
            (byte) 0x00,
            (byte) 0x06    };

    /***************************************************************************************     * ALM COMMANDS     ***************************************************************************************/    public static final byte[] cmd_readALMConfiguration = new byte[] {
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0x40,
            (byte) 0x01    };

    public static final byte[] cmd_readI2cAddr = new byte[]{
            (byte) 0x12,
            (byte) 0xC0,
            (byte) 0x04,
            (byte) 0x3e,
            (byte) 0x01    };

}

