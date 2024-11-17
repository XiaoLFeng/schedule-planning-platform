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

import {useSelector} from "react-redux";
import {WebInfoEntity} from "../../../models/entity/web_info_entity.ts";
import {useNavigate} from "react-router-dom";
import {JSX, useEffect, useState} from "react";
import {GetClassMyTimeAPI, GetClassTimeMarketAPI} from "../../../interface/curriculum_api.ts";
import {PageDTO} from "../../../models/dto/page_dto.ts";
import {Page} from "../../../models/page.ts";
import {ClassTimeEntity} from "../../../models/entity/class_time_entity.ts";
import {StarOutlined} from "@ant-design/icons";
import dayjs from "dayjs";

export function DashboardCurriculumTime({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const navigate = useNavigate();

    const [timeMarketPage, setTimeMarketPage] = useState<PageDTO>({page: 1, size: 8} as PageDTO);
    const [timeMyPage, setTimeMyPage] = useState({page: 1, size: 16} as PageDTO);
    const [timeMarket, setTimeMarket] = useState<Page<ClassTimeEntity>>({} as Page<ClassTimeEntity>);
    const [timeMy, setTimeMy] = useState<Page<ClassTimeEntity>>({} as Page<ClassTimeEntity>);

    document.title = `${webInfo.name} - 课程时间`;
    onHeaderHandler("课程时间");

    useEffect(() => {
        const funcMarketTime = async () => {
            const getResp = await GetClassTimeMarketAPI(timeMarketPage);
            if (getResp?.output === "Success") {
                setTimeMarket(getResp.data!);
            } else {
                console.error(getResp?.error_message);
            }
        }
        const funcMyTime = async () => {
            const getResp = await GetClassMyTimeAPI(timeMyPage);
            if (getResp?.output === "Success") {
                setTimeMy(getResp.data!);
            } else {
                console.error(getResp?.error_message);
            }
        }

        funcMarketTime().then();
        funcMyTime().then();
    }, [timeMarketPage, timeMyPage]);

    function handleMyTimePage(page: number) {
        setTimeMyPage({...timeMyPage, page: page});
    }

    function handleMarketTimePage(page: number) {
        setTimeMarketPage({...timeMarketPage, page: page});
    }

    function pageMyTimeReveal(): JSX.Element[] {
        const pages = [];
        for (let i = 0; i < timeMy.pages; i++) {
            if (timeMy.current === +1) {
                pages.push(
                    <li key={i}>
                        <button onClick={() => handleMyTimePage(i + 1)}
                                className="block size-8 rounded border border-sky-500 bg-sky-500 text-center leading-8 text-white">
                            {i + 1}
                        </button>
                    </li>
                );
            } else {
                pages.push(
                    <li key={i}>
                        <button
                            className="block size-8 rounded border border-gray-100 bg-white text-center leading-8 text-gray-900">
                            {i + 1}
                        </button>
                    </li>
                );
            }
        }
        return pages
    }

    function pageMarketTimeReveal(): JSX.Element[] {
        const pages = [];
        for (let i = 0; i < timeMarket.pages; i++) {
            if (timeMarket.current === +1) {
                pages.push(
                    <li key={i}>
                        <button onClick={() => handleMarketTimePage(i + 1)}
                                className="block size-8 rounded border border-sky-500 bg-sky-500 text-center leading-8 text-white">
                            {i + 1}
                        </button>
                    </li>
                );
            } else {
                pages.push(
                    <li key={i}>
                        <button
                            className="block size-8 rounded border border-gray-100 bg-white text-center leading-8 text-gray-900">
                            {i + 1}
                        </button>
                    </li>
                );
            }
        }
        return pages
    }

    return (
        <div className={"grid gap-3"}>
            <div className={"flex gap-3 justify-end"}>
                <button onClick={() => navigate("/dashboard/curriculum")}
                        className={"bg-emerald-500 hover:bg-emerald-600 text-white rounded-lg px-4 py-1.5 transition"}>
                    返回课程表
                </button>
                <button className={"bg-sky-500 hover:bg-sky-600 text-white rounded-lg px-4 py-1.5 transition"}>
                    创建时间表
                </button>
            </div>
            <hr className={"border-2 rounded-full mx-12"}/>
            <div className={"grid gap-1"}>
                <div className={"text-xl font-medium"}>我的时间表</div>
                <div className={"grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 gap-3"}>
                    {
                        timeMy.records?.map((item, index) => {
                            return (
                                <div key={index}
                                     className={"bg-white rounded-lg shadow-md p-3 flex flex-col gap-1"}>
                                    <div className={"text-lg font-medium"}>{item.name}</div>
                                    <div className={"text-xs text-gray-400 mb-3"}>
                                        {item.is_public ? "公开" : "未公开"}{item.is_official ? " | 认证" : null}
                                    </div>
                                    <div className={"flex gap-1 justify-end"}>
                                        <button
                                            className={"bg-sky-500 hover:bg-sky-600 text-white rounded-lg px-2.5 py-0.5 transition"}>
                                            查看
                                        </button>
                                        {
                                            item.is_official || item.user_uuid == null ? (
                                                <button
                                                    className={"bg-gray-300 text-gray-900 rounded-lg px-2.5 py-0.5 transition"}>
                                                    编辑
                                                </button>
                                            ) : (
                                                <button
                                                    className={"bg-emerald-500 hover:bg-emerald-600 text-white rounded-lg px-2.5 py-0.5 transition"}>
                                                    编辑
                                                </button>
                                            )
                                        }
                                        {
                                            item.is_official || item.user_uuid == null ? (
                                                <button
                                                    className={"bg-gray-300 text-gray-900 rounded-lg px-2.5 py-0.5 transition"}>
                                                    删除
                                                </button>
                                            ) : (
                                                <button
                                                    className={"bg-red-500 hover:bg-red-600 text-white rounded-lg px-2.5 py-0.5 transition"}>
                                                    删除
                                                </button>
                                            )
                                        }
                                    </div>
                                </div>
                            );
                        })
                    }
                </div>
                <ol className="flex justify-center gap-1 text-xs font-medium">
                    <li>
                        <button
                            onClick={() => handleMyTimePage(1)}
                            className={"inline-flex size-8 items-center justify-center rounded border border-gray-200 bg-white hover:bg-gray-200 text-gray-900 transition"}
                        >
                            <span className="sr-only">前一页</span>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="size-3"
                                viewBox="0 0 20 20"
                                fill="currentColor"
                            >
                                <path
                                    fillRule="evenodd"
                                    d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
                                    clipRule="evenodd"
                                />
                            </svg>
                        </button>
                    </li>
                    {pageMyTimeReveal()}
                    <li>
                        <button
                            onClick={() => handleMyTimePage(timeMy.pages)}
                            className={"inline-flex size-8 items-center justify-center rounded border border-gray-200 bg-white hover:bg-gray-200 text-gray-900 transition"}
                        >
                            <span className="sr-only">下一页</span>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="size-3"
                                viewBox="0 0 20 20"
                                fill="currentColor"
                            >
                                <path
                                    fillRule="evenodd"
                                    d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                                    clipRule="evenodd"
                                />
                            </svg>
                        </button>
                    </li>
                </ol>
            </div>
            <div className={"grid gap-1"}>
                <div className={"text-xl font-medium"}>时间表市场</div>
                <div className={"grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 gap-3"}>
                    {
                        timeMarket.records?.map((item, index) => {
                            return (
                                <div key={index}
                                     className={"bg-white rounded-lg shadow-md p-3 flex flex-col gap-1"}>
                                    <div className={"text-lg font-medium"}>{item.name}</div>
                                    {
                                        item.is_official ? (
                                            <div className={"text-yellow-500 flex space-x-1 items-center"}>
                                                <StarOutlined/>
                                                <span>{item.is_official ? "认证" : null}</span>
                                            </div>
                                        ) : (
                                            <div className={"text-gray-400 flex space-x-1 items-center"}>
                                                <StarOutlined/>
                                                <span>{dayjs(item.created_at).format("YYYY-MM-DD")}</span>
                                            </div>
                                        )
                                    }
                                </div>
                            );
                        })
                    }
                </div>
                <ol className="flex justify-center gap-1 text-xs font-medium">
                    <li>
                        <button
                            onClick={() => handleMarketTimePage(1)}
                            className={"inline-flex size-8 items-center justify-center rounded border border-gray-200 bg-white hover:bg-gray-200 text-gray-900 transition"}
                        >
                            <span className="sr-only">前一页</span>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="size-3"
                                viewBox="0 0 20 20"
                                fill="currentColor"
                            >
                                <path
                                    fillRule="evenodd"
                                    d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
                                    clipRule="evenodd"
                                />
                            </svg>
                        </button>
                    </li>
                    {pageMarketTimeReveal()}
                    <li>
                        <button
                            onClick={() => handleMarketTimePage(timeMarket.pages)}
                            className={"inline-flex size-8 items-center justify-center rounded border border-gray-200 bg-white hover:bg-gray-200 text-gray-900 transition"}
                        >
                            <span className="sr-only">下一页</span>
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="size-3"
                                viewBox="0 0 20 20"
                                fill="currentColor"
                            >
                                <path
                                    fillRule="evenodd"
                                    d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                                    clipRule="evenodd"
                                />
                            </svg>
                        </button>
                    </li>
                </ol>
            </div>
        </div>
    );
}
