/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

const RomanI = ({color}: { color?: string }) => {
    const clazz = color ? `size-4 fill-${color}` : "size-4";
    return (
        <svg viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1321"
             className={clazz}>
            <path d="M448 277.333333v469.333334h128v-469.333334h-128z"
                  p-id="1322"></path>
        </svg>
    );
};

const RomanII = ({color}: { color?: string }) => {
    const clazz = color ? `size-4 fill-${color}` : "size-4";
    return (
        <svg viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1178" className={clazz}>
            <path
                d="M298.666667 746.666667l94.634666-469.333334h110.208l-94.634666 469.333334H298.666667zM520.490667 746.666667l94.634666-469.333334H725.333333l-94.634666 469.333334h-110.208z"
                p-id="1179"></path>
        </svg>
    );
};

const RomanIII = ({color}: { color?: string }) => {
    const clazz = color ? `size-4 fill-${color}` : "size-4";
    return (
        <svg viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1604" className={clazz}>
            <path
                d="M234.666667 277.333333v469.333334h123.52v-469.333334H234.666667zM450.218667 277.333333v469.333334h123.562666v-469.333334h-123.562666zM665.813333 277.333333v469.333334H789.333333v-469.333334h-123.52z"
                p-id="1605"></path>
        </svg>
    );
};

const RomanIV = ({color}: { color?: string }) => {
    const clazz = color ? `size-4 fill-${color}` : "size-4";
    return (
        <svg viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1747" className={clazz}>
            <path
                d="M213.333333 277.333333v469.333334h126.805334v-469.333334H213.333333zM386.090667 277.333333l188.458666 469.333334h131.626667L896 277.333333h-130.261333l-121.941334 339.456L522.581333 277.333333h-136.533333z"
                p-id="1748"></path>
        </svg>
    );
};
export {RomanI, RomanII, RomanIII, RomanIV};
